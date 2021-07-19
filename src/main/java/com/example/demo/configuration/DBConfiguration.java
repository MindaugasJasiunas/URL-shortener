package com.example.demo.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@PropertySource("classpath:db.properties")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="com.example.demo", entityManagerFactoryRef ="entityManagerFactory", transactionManagerRef = "transactionManager")
public class DBConfiguration {
    @Autowired
    private Environment env;

    // To use Spring Data JPA in Spring project
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(env.getProperty("db.packages.to.scan"));

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(env.getProperty("db.packages.to.scan"));
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config= new HikariConfig();
        config.setDriverClassName(env.getProperty("db.driver.class.name"));
        config.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(env.getProperty("db.maximum.pool.size"))));
        config.setMinimumIdle(Integer.parseInt(Objects.requireNonNull(env.getProperty("db.minimum.idle"))));
        config.setUsername(env.getProperty("db.username"));
        config.setPassword(env.getProperty("db.password"));
        config.setJdbcUrl(env.getProperty("db.jdbcurl"));

        config.addDataSourceProperty("cachePrepStmts", env.getProperty("db.cachePrepStmts"));
        config.addDataSourceProperty("prepStmtCacheSize", env.getProperty("db.prepStmtCacheSize"));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", env.getProperty("db.prepStmtCacheSqlLimit"));

        return new HikariDataSource(config);
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("db.hibernate.hbm2ddl.auto"));

        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("db.hibernate.dialect"));

        return hibernateProperties;
    }
}
