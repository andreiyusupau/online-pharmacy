package com.vironit.onlinepharmacy.config;

/**
 * @deprecated Replaced with Spring Boot.
 */
@Deprecated
//@ComponentScan(basePackages = "com.vironit.onlinepharmacy")
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories("com.vironit.onlinepharmacy.repository")
public class DatabaseConfiguration {
//
//    public static final String PACKAGES_TO_SCAN = "com.vironit.onlinepharmacy.model";
//    private static final String JDBC_CONFIGURATION_FILE = "/jdbc.properties";
//    private static final String HIBERNATE_CONFIGURATION_FILE = "/hibernate.properties";
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean
//                = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource());
//        entityManagerFactoryBean.setPackagesToScan(PACKAGES_TO_SCAN);
//        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
//        entityManagerFactoryBean.setJpaProperties(additionalProperties());
//        return entityManagerFactoryBean;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        HikariConfig hikariConfig = new HikariConfig(JDBC_CONFIGURATION_FILE);
//        return new HikariDataSource(hikariConfig);
//    }
//
//    private JpaVendorAdapter jpaVendorAdapter() {
//        return new HibernateJpaVendorAdapter();
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return transactionManager;
//    }
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
//    private Properties additionalProperties() {
//        try (InputStream inputStream = getClass().getResourceAsStream(HIBERNATE_CONFIGURATION_FILE)) {
//            Properties properties = new Properties();
//            properties.load(inputStream);
//            return properties;
//        } catch (IOException ex) {
//            throw new ConfigurationException("Can't load configuration.", ex);
//        }
//    }
}
