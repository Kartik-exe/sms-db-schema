package com.schema.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    private final String SPRING_DATASOURCE = "spring.datasource.";

    @Autowired
    private Environment environment;

    @Value("${db.connection.site.id}")
    private String siteId;

    @Bean(name = "dataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource customDataSource(){
        return getConnection(siteId);
    }

    private DataSource getConnection(String siteId) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(environment.getProperty(SPRING_DATASOURCE + siteId + ".driver-class-name"));
        dataSource.setJdbcUrl(environment.getProperty(SPRING_DATASOURCE + siteId + ".jdbcUrl"));
        dataSource.setUsername(environment.getProperty(SPRING_DATASOURCE + siteId + ".username"));
        dataSource.setPassword(environment.getProperty(SPRING_DATASOURCE + siteId + ".password"));
        dataSource.setAllowPoolSuspension(true);
        dataSource.setMinimumIdle(Integer.parseInt(environment.getProperty(SPRING_DATASOURCE + siteId + ".max-pool-size")));
        dataSource.setConnectionTimeout(Integer.parseInt(environment.getProperty(SPRING_DATASOURCE + siteId + ".connection-timeout")));
        dataSource.setMaxLifetime(Integer.parseInt(environment.getProperty(SPRING_DATASOURCE + siteId + ".max-life")));
        dataSource.setConnectionTestQuery(environment.getProperty(SPRING_DATASOURCE + siteId + ".validationQuery"));
        dataSource.addDataSourceProperty("cachePrepStmts", true);
        dataSource.addDataSourceProperty("prepStmtCacheSize", 25000);
        dataSource.addDataSourceProperty("userServerPrepStmts", true);
        dataSource.addDataSourceProperty("initializationFailFast", true);
        dataSource.setPoolName(environment.getProperty(SPRING_DATASOURCE +siteId + ".poolName"));

        return dataSource;
    }
}
