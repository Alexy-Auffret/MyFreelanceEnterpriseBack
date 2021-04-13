package fr.cesi.myFreelanceEntreprise.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class BillDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BillDAO(@Qualifier("springDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


}
