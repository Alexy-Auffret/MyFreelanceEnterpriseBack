package fr.cesi.myfreelanceenterpriseback.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
public class CommonTest {
    @Configuration
    @EnableAutoConfiguration()
    @ComponentScan(value = { "fr.cesi" })
    public static class H2Configuration {

    }

    @Autowired
    @Qualifier("spingDataSource")
    protected DataSource dataSource;

    @Before
    public void init() throws Exception, IOException {
        executeScript("create_freelance_entreprise.sql");
        executeScript("populate_freelance_entreprise.sql");
    }

    @After
    public void after() throws SQLException, IOException {
        executeScript("destroy.sql");
    }

    protected void executeScript(String script) throws SQLException, IOException {
        try (Connection connection = dataSource.getConnection();
            InputStreamReader reader = new InputStreamReader(ResourceUtils.getURL("classpath:" + script).openStream())) {
                RunScript.execute(connection, reader);
            }
    }
}
