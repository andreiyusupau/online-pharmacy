package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.ProductDao;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class JpaProductDaoTest {

    @Container
    private PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private ProductDao productDao;

    private Product product;
    private Product secondProduct;
    private Product thirdProduct;
    private ProductCategory productCategory;

    @BeforeEach
    void set() {
        productCategory = new ProductCategory(1, "medicine", "any medicine");
        product = new Product();
        product.setName("testProduct");
        product.setPrice(new BigDecimal("100"));
        product.setProductCategory(productCategory);
        product.setRecipeRequired(false);
        secondProduct = new Product();
        secondProduct.setName("secondTestProduct");
        secondProduct.setPrice(new BigDecimal("120"));
        secondProduct.setProductCategory(productCategory);
        secondProduct.setRecipeRequired(false);
        thirdProduct = new Product();
        thirdProduct.setName("thirdTestProduct");
        thirdProduct.setPrice(new BigDecimal("150"));
        thirdProduct.setProductCategory(productCategory);
        thirdProduct.setRecipeRequired(false);
    }

    @Test
    void addShouldAddProductToDatabase() {
        long id = productDao.add(secondProduct);

        assertEquals(1, id);
    }

    @Test
    void getShouldGetProductFromDatabase() {
        productDao.add(product);

        Product acquiredProduct = productDao.get(1)
                .get();

        assertEquals(product.getName(), acquiredProduct.getName());
    }

    @Test
    void getAllShouldGetAllProductsFromDatabase() {
        productDao.add(product);
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
        productDao.add(product);

        productDao.remove(1);

        long sizeAfterRemove = productDao.getAll()
                .size();
        assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualThree() {
        productDao.add(product);
        productDao.add(secondProduct);
        productDao.add(thirdProduct);

        long totalElements = productDao.getTotalElements();

        assertEquals(3, totalElements);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualOne() {
        productDao.add(product);

        long totalElements = productDao.getTotalElements();

        assertEquals(1, totalElements);
    }

    @Test
    void getPageShouldReturnASecondPageWithOneElement() {
        productDao.add(product);
        productDao.add(secondProduct);
        productDao.add(thirdProduct);

        Collection<Product> productPage = productDao.getPage(2, 2);

        int pageSize = productPage.size();
        assertEquals(1, pageSize);
    }

    @Test
    void getPageShouldReturnAThirdPageWithNoElements() {
        productDao.add(product);
        productDao.add(secondProduct);
        productDao.add(thirdProduct);

        Collection<Product> productPage = productDao.getPage(3, 2);

        int pageSize = productPage.size();
        assertEquals(0, pageSize);
    }
}
