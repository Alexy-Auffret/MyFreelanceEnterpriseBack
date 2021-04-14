package fr.cesi.myFreelanceEntreprise.dao;

import fr.cesi.myFreelanceEntreprise.beans.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import fr.cesi.myFreelanceEntreprise.tools.NamedQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClientDAOTest {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientDAOTest(@Qualifier("springDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Client> getAll() {
        return new NamedQuery(jdbcTemplate, "getAllClient", "queries.sql").query(new RowMapper<Client>() {
            @Override
            public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
                Client cli = new Client();
                cli.setId(rs.getInt("id"));
                cli.setName(rs.getString("name"));
                cli.setAddress(rs.getString("adresse"));
                cli.setPhoneNumber(rs.getString("phone_number"));
                cli.setActive(rs.getBoolean("active"));
                return cli;
            }
        });
    }
}
