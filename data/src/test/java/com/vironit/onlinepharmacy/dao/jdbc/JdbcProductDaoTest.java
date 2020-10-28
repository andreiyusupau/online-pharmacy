package com.vironit.onlinepharmacy.dao.jdbc;

import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.ProductCategory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class JdbcProductDaoTest {

    @Container
    private PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("schema.sql");

    private DataSource dataSource;
    private JdbcProductDao productDao;

    private Product product;
    private Product secondProduct;
    private Product thirdProduct;
    private ProductCategory productCategory;

    @BeforeEach
    void set() throws SQLException {
        dataSource = getDataSource(postgreSqlContainer);
        productDao = new JdbcProductDao(dataSource);
        productCategory = new ProductCategory(1, "medicine", "any medicine");
        product = new Product(-1, "testProduct", new BigDecimal("100"), productCategory, false);
        secondProduct = new Product(-1, "secondTestProduct", new BigDecimal("120"), productCategory, false);
        thirdProduct = new Product(-1, "thirdTestProduct", new BigDecimal("150"), productCategory, false);
        String sql = "INSERT INTO product_categories (name, description) " +
                "VALUES('medicine','any medicine');\n" +
                "INSERT INTO products (name, price, recipe_required, product_category_id) " +
                "VALUES('testProduct','10000','true',1);";
        executeUpdate(dataSource, sql);
    }

    @Test
    void addShouldAddProductToDatabase() {
        long id = productDao.add(secondProduct);

        assertEquals(2, id);
    }

    @Test
    void getShouldGetProductFromDatabase() {
        Product acquiredProduct = productDao.get(1)
                .get();

        assertEquals(product.getName(), acquiredProduct.getName());
    }

    @Test
    void getAllShouldGetAllProductsFromDatabase() {
        productDao.add(secondProduct);
        productDao.add(thirdProduct);

        Collection<Product> acquiredProducts = productDao.getAll();
        int actualSize = acquiredProducts.size();
        assertEquals(3, actualSize);
    }

    @Test
    void updateShouldUpdateProductInDatabase() {
        productDao.add(product);
        Product productForUpdate = new Product(1, "updatedTestProduct", new BigDecimal("180.00"), productCategory, false);

        productDao.update(productForUpdate);

        Product updatedProduct = productDao.get(1)
                .get();

        assertEquals(productForUpdate.getName(), updatedProduct.getName());
        assertEquals(productForUpdate.getPrice(), updatedProduct.getPrice());
    }

    @Test
    void removeShouldRemoveProductFromDatabase() {
        productDao.remove(1);
        long sizeAfterRemove = productDao.getAll()
                .size();

        assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualThree() {
        productDao.add(secondProduct);
        productDao.add(thirdProduct);
        long totalElements = productDao.getTotalElements();
        assertEquals(3, totalElements);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualOne() {
        long totalElements = productDao.getTotalElements();
        assertEquals(1, totalElements);
    }

    @Test
    void getPageShouldReturnASecondPageWithOneElement() {
        productDao.add(secondProduct);
        productDao.add(thirdProduct);

        Collection<Product> productPage = productDao.getPage(2, 2);

        int pageSize = productPage.size();
        assertEquals(1, pageSize);
    }

    @Test
    void getPageShouldReturnAThirdPageWithNoElements() {
        productDao.add(secondProduct);
        productDao.add(thirdProduct);

        Collection<Product> productPage = productDao.getPage(3, 2);

        int pageSize = productPage.size();
        assertEquals(0, pageSize);
    }

    private int executeUpdate(DataSource dataSource, String sql) throws SQLException {
        try (Statement statement = dataSource.getConnection()
                .createStatement()) {
            return statement.executeUpdate(sql);
        }
    }

    private DataSource getDataSource(JdbcDatabaseContainer<?> container) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());
        hikariConfig.setDriverClassName(container.getDriverClassName());
        return new HikariDataSource(hikariConfig);
    }
}
