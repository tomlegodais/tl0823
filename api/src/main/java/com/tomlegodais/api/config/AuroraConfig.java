package com.tomlegodais.api.config;

import com.tomlegodais.api.service.SecretManagerService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class AuroraConfig {

    @Value("${AURORA_SECRET_ARN}")
    private String secretArn;

    private final SecretManagerService secretManagerService;

    public AuroraConfig(SecretManagerService secretManagerService) {
        this.secretManagerService = secretManagerService;
    }

    @Bean
    public HikariConfig hikariConfig() {
        var secrets = secretManagerService.getSecretValues(secretArn, "username", "password", "host", "port", "dbname");
        var config = new HikariConfig();

        config.setJdbcUrl(this.buildJdbcUrl(secrets));
        config.setUsername((String) secrets.get("username"));
        config.setPassword((String) secrets.get("password"));
        config.setDriverClassName("org.postgresql.Driver");
        return config;
    }

    public String buildJdbcUrl(Map<String, Object> secrets) {
        var host = (String) secrets.get("host");
        var port = (Integer) secrets.get("port");
        var dbname = (String) secrets.get("dbname");

        return String.format("jdbc:postgresql://%s:%d/%s", host, port, dbname);
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }
}
