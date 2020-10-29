package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.ProductCategoryDao;
import com.vironit.onlinepharmacy.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class JpaProductCategoryDaoTest {

    @Container
    private PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test");

    @Autowired
private ProductCategoryDao productCategoryDao;

    private ProductCategory productCategory;

    @BeforeEach
    void set(){
        productCategory=new ProductCategory();
        productCategory.setName("product category");
        productCategory.setDescription("description");
    }

    @Test
    void addShouldAddRecipeToCollection() {
        long id = productCategoryDao.add(productCategory);

        long sizeAfterAdd = productCategoryDao.getAll()
                .size();
        assertEquals(1, sizeAfterAdd);
        assertEquals(1, id);
    }

    @Test
    void getShouldGetRecipeFromCollection() {
        long id = productCategoryDao.add(productCategory);

        ProductCategory actualProductCategory = productCategoryDao.get(id)
                .get();

        assertEquals(productCategory, actualProductCategory);
    }

    @Test
    void getAllShouldGetAllRecipesFromCollection() {
        productCategoryDao.add(productCategory);
        productCategoryDao.add(productCategory);
        productCategoryDao.add(productCategory);

        Collection<ProductCategory> acquiredProductCategories = productCategoryDao.getAll();

        long sizeAfterAdd = productCategoryDao.getAll()
                .size();
        assertEquals(3, sizeAfterAdd);
    }

    @Test
    void updateShouldUpdateProductCategory() {
        productCategoryDao.add(productCategory);
        ProductCategory productCategoryForUpdate = new ProductCategory(1, "updated","updated");

        productCategoryDao.update(productCategoryForUpdate);

        ProductCategory updatedProductCategory = productCategoryDao.get(1)
                .get();
        assertEquals(productCategoryForUpdate, updatedProductCategory);
    }

    @Test
    void removeShouldRemoveRecipeFromCollection() {
        productCategoryDao.add(productCategory);

        productCategoryDao.remove(1);
        long sizeAfterRemove = productCategoryDao.getAll()
                .size();

        assertEquals(0, sizeAfterRemove);
    }
}
