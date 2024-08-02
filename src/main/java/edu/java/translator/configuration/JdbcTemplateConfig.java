package edu.java.translator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {

    @Bean
    public JdbcTemplate JdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
