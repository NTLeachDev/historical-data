package com.nleachdev.historicaldata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class PostgresDbConfig {

    @Bean
    public DataSource postgresDataSource(@Value("${historicaldata.postgres.connection.url}") final String url,
                                         @Value("${historicaldata.postgres.connection.driver}") final String driver,
                                         @Value("${historicaldata.postgres.connection.username}") final String username,
                                         @Value("${historicaldata.postgres.connection.password}") final String password) {
        return DataSourceBuilder.create()
                .url(url)
                .driverClassName(driver)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate postgresNamedTemplate(final DataSource postgresDataSource) {
        return new NamedParameterJdbcTemplate(postgresDataSource);
    }

    @Bean
    public DataSourceTransactionManager postgresTransactionManager(final DataSource postgresDataSource) {
        return new DataSourceTransactionManager(postgresDataSource);
    }
}
