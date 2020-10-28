package com.vironit.onlinepharmacy.dao.jpa;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ComponentScan(basePackages = "com.vironit.onlinepharmacy")
@Configuration
@EnableTransactionManagement
public class ApplicationConfigurationTest {

    public static final String PACKAGES_TO_SCAN = "com.vironit.onlinepharmacy.model";
    private static final String JDBC_CONFIGURATION_FILE = "src/test/resources/jdbc.properties";
    private static final String HIBERNATE_CONFIGURATION_FILE = "src/test/resources/hibernate.properties";

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean
                = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(PACKAGES_TO_SCAN);

        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(additionalProperties());

        return entityManagerFactoryBean;
    }

    private DataSource dataSource(){
        HikariConfig hikariConfig = new HikariConfig(JDBC_CONFIGURATION_FILE);
        return new HikariDataSource(hikariConfig);
    }

    private JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//        return new PersistenceExceptionTranslationPostProcessor();
//    }

    private Properties additionalProperties() {
         try (InputStream input = new FileInputStream(HIBERNATE_CONFIGURATION_FILE)) {
            Properties properties = new Properties();
            properties.load(input);
             return properties;
        } catch (IOException ex) {
             //TODO:own exception
            throw new NullPointerException();
        }
    }

//    @Bean
//    public EntityManager entityManager(EntityManagerFactory entityManagerFactory){
//        return entityManagerFactory.createEntityManager();
//    }

}
