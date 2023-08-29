package com.tomlegodais.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceValidationConfig {

    private final DataSource dataSource;

    public DataSourceValidationConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public void validateDataSourceConnection() {
        Logger logger = LoggerFactory.getLogger(DataSourceValidationConfig.class);

        try {
            var jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);

            logger.info("Successfully validated data source connection.");
        } catch (Exception e) {
            logger.error("Failed to validate data source connection.", e);
            throw new RuntimeException("Failed to validate data source connection.", e);
        }
    }
}
