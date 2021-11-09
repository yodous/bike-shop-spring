package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

//@Configuration
public class DbConfig {
//    @Bean
    public DataSource createDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriver(driver);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
        return dataSource;
    }

}
