package fr.cesi.myFreelanceEntreprise.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;


public class NamedQuery {
    private static class NamedQueryCache {
        private boolean reloadable = true;

        private long lastModified = 0L;

        private Map<String, String> queries = new HashMap<String, String>();

        private synchronized void parse(String resourceFileName) {
            if (!reloadable)
                return;

            long last = 1L;

            URL url = Thread.currentThread().getContextClassLoader().getResource(resourceFileName);

            if (url != null) {
                try {
                    File f = new File(url.getFile());

                    if (f.exists())
                        last = f.lastModified();
                } catch (Exception e) {
                    reloadable = false;
                }
            } else
                throw new DataSourceLookupFailureException("Erreur lecture du fichier " + resourceFileName);

            if (last > lastModified) {
                lastModified = last;

                try {
                    logger.info("(Re-)chargement requêtes contenues dans " + url.toString());

                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                    StringBuilder sb = new StringBuilder();

                    try {
                        String line = null;
                        String currentQueryName = null;

                        while ((line=br.readLine()) != null) {
                            String trimmedLine = line.trim();

                            if (currentQueryName != null && trimmedLine.length() > 0 && !trimmedLine.startsWith("--")) {
                                if (sb.length() > 0)
                                    sb.append("\n");

                                sb.append(line);
                            }
                            if (trimmedLine.startsWith("--") && trimmedLine.substring(2).trim().startsWith("@")) {
                                if (currentQueryName != null)
                                    queries.put(currentQueryName, sb.toString());

                                currentQueryName = line.substring(2).trim().substring(1).trim();
                                sb = new StringBuilder();
                            }
                        }

                        if (currentQueryName != null)
                            queries.put(currentQueryName, sb.toString());

                    } catch (IOException e) {
                        throw new DataSourceLookupFailureException("Erreur chargement SQL", e);
                    } finally {
                        if (queries.size() > 0)
                            logger.info("Requêtes trouvées: " + queries.keySet());

                        try {
                            if (br != null)
                                br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    throw new DataSourceLookupFailureException("Erreur lors de la lecture du fichier " + url.toString(), e);
                }
            }
        }
    }
    private final static Logger logger = LogManager.getLogger(NamedQuery.class);

    private static Map<String, NamedQueryCache> queryCache = new HashMap<String, NamedQueryCache>();

    private static Pattern aliasPattern = Pattern.compile("(\\{[^}]+\\})", Pattern.DOTALL);

    private static SimpleDateFormat oracleToDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private HashMap<String, String> aliases;

    private List<MapSqlParameterSource> batchBindParameters;

    private MapSqlParameterSource bindParameters;

    private NamedParameterJdbcTemplate template;

    private String sql;
    private String queryName;
    private String resourceName;

    public NamedQuery(JdbcTemplate jdbcTemplate, String queryName, String resourceName) throws DataAccessException {
        this.queryName = queryName;
        this.resourceName = resourceName;
        this.template = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.bindParameters = new MapSqlParameterSource();
        this.aliases = new HashMap<String, String>();

        if (resourceName != null && resourceName.startsWith("@")) {
            sql = queryName;
            return;
        }
        if (this.resourceName == null || this.queryName == null) {
            throw new NullPointerException();
        }

        NamedQueryCache cache = null;

        synchronized (queryCache) {
            cache = queryCache.get(resourceName);

            if (cache == null) {
                cache = new NamedQueryCache();
                queryCache.put(resourceName, cache);
            }
        }

        cache.parse(resourceName);

        sql = cache.queries.get(this.queryName);

        if (sql == null || sql.length() == 0)
            throw new DataSourceLookupFailureException("Requête '" + this.queryName + "' non trouvée !");
    }

    public NamedQuery bind(String key, Object value) {
        bindParameters.addValue(key, value);
        return this;
    }

    public NamedQuery bind(String key, Object value, int sqlType) {
        bindParameters.addValue(key, value, sqlType);
        return this;
    }

    public NamedQuery bind(Map<String, Object> multipleValues) {
        for(String key:multipleValues.keySet()) {
            bindParameters.addValue(key, multipleValues.get(key));
        }
        return this;
    }

    public NamedQuery bind(Map<String, Object> multipleValues, Map<String, Integer> sqlTypes) {
        bind(multipleValues);

        for(String key:sqlTypes.keySet())
            bindParameters.registerSqlType(key, sqlTypes.get(key));

        return this;
    }

    public NamedQuery bind(SqlParameterSource parameterSource) {
        for(String paramName:parameterSource.getParameterNames())
            bindParameters.addValue(paramName, parameterSource.getValue(paramName), parameterSource.getSqlType(paramName));
        return this;
    }

    private void prepare() {
        Matcher m = aliasPattern.matcher(sql);

        while (m.find()) {
            String alias = m.group().replace("{", "").replace("}", "");
            String content = aliases.get(alias);

            if (content == null) {
                content = "";
                logger.warn("Alias '" + alias + "' non déclaré ! Remplacement par une chaîne vide");
            }

            sql = sql.replace(m.group(), content);
        }

        if (logger.isDebugEnabled()) {
            String trace = sql;
            ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(trace);
            List<MapSqlParameterSource> allparams = new ArrayList<MapSqlParameterSource>();

            if (batchBindParameters != null)
                allparams.addAll(batchBindParameters);
            else
                allparams.add(bindParameters);

            for(MapSqlParameterSource source:allparams) {
                trace = NamedParameterUtils.substituteNamedParameters(parsedSql, source);
                Object values [] = NamedParameterUtils.buildValueArray(parsedSql, source, null);
                List<Object> valList = new ArrayList<>();

                for(Object obj:values) {
                    if (obj instanceof Collection)
                        valList.addAll((Collection<?>) obj);
                    else
                        valList.add(obj);
                }

                values = valList.toArray(new Object[valList.size()]);
                String parts[] = trace.split("\\?");

                if (parts.length == (values.length + 1)) {
                    StringBuilder sb = new StringBuilder();

                    for(int i=0;i<parts.length;i++) {
                        sb.append(parts[i]);

                        if (i < values.length) {
                            if (values[i] == null)
                                sb.append("NULL");
                            else if (values[i] instanceof Date) {
                                sb.append("TO_DATE('");
                                sb.append(oracleToDateFormat.format((Date) values[i]));
                                sb.append("', 'YYYY/MM/DD HH24:MI:SS')");
                            } else if (values[i] instanceof String) {
                                sb.append("'");
                                sb.append(values[i]);
                                sb.append("'");
                            } else if (values[i] instanceof Boolean)
                                sb.append(((Boolean) values[i]) == Boolean.TRUE?"TRUE":"FALSE");
                            else
                                sb.append(values[i]);
                        }
                    }
                    trace = sb.toString();
                }

                logger.debug("[" + queryName + "] " + trace);
            }
        }
    }

    public <T> List<T> query(RowMapper<T> rowMapper) throws DataAccessException {
        prepare();
        return template.query(sql, bindParameters, rowMapper);
    }

    public <T> T queryForObject(RowMapper<T> rowMapper) throws DataAccessException {
        prepare();
        return template.queryForObject(sql, bindParameters, rowMapper);
    }

    public <T> T findObject(RowMapper<T> rowMapper) throws DataAccessException {
        T t = null;
        try {
            t = queryForObject(rowMapper);
        } catch (EmptyResultDataAccessException e) {
            // tant pis, l'objet n'existe pas, on renvoit nul
        }
        return t;
    }

    public <T> T query(ResultSetExtractor<T> rse) throws DataAccessException {
        prepare();
        return template.query(sql, bindParameters, rse);
    }

    public void query(RowCallbackHandler rch) throws DataAccessException {
        prepare();
        template.query(sql, bindParameters, rch);
    }

    public int update() throws DataAccessException {
        prepare();
        return template.update(sql, bindParameters);
    }

    public int update(KeyHolder generatedKeyHolder) throws DataAccessException {
        prepare();
        return template.update(sql, bindParameters, generatedKeyHolder);
    }

    public int update(KeyHolder generatedKeyHolder, String keyColumnsNames[]) throws DataAccessException {
        prepare();
        return template.update(sql, bindParameters, generatedKeyHolder, keyColumnsNames);
    }

    public <T> T queryForObject(Class<T> requiredType) throws DataAccessException {
        prepare();
        return template.queryForObject(sql, bindParameters, requiredType);
    }

    public <T> List<T> queryForList(Class<T> elementType) throws DataAccessException {
        prepare();
        return template.queryForList(sql, bindParameters, elementType);
    }

    public List<Map<String, Object>> queryForList() throws DataAccessException {
        prepare();
        return template.queryForList(sql, bindParameters);
    }

    public Map<String, Object> queryForMap() throws DataAccessException {
        prepare();
        return template.queryForMap(sql, bindParameters);
    }

    public SqlRowSet queryForRowSet() throws DataAccessException {
        prepare();
        return template.queryForRowSet(sql, bindParameters);
    }
}