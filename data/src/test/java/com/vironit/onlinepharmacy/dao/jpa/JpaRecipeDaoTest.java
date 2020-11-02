package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.RecipeDao;
import com.vironit.onlinepharmacy.model.OrderPosition;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.Recipe;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class JpaRecipeDaoTest {

    @Container
    private PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private RecipeDao recipeDao;

    private OrderPosition orderPosition;
    private OrderPosition secondOrderPosition;
    private OrderPosition thirdOrderPosition;
    private Recipe recipe;
    private Recipe secondRecipe;
    private Recipe thirdRecipe;
    private Product product;

    @BeforeEach
    void set() {
        product = new Product(1, "product", new BigDecimal(123), null, true);
        orderPosition = new OrderPosition(1, 2, product, null);
        secondOrderPosition = new OrderPosition(2, 5, product, null);
        thirdOrderPosition = new OrderPosition(3, 7, product, null);
        recipe = new Recipe();
        recipe.setDescription("recipe");
        recipe.setQuantity(2);
        recipe.setProduct(product);
        recipe.setValidThru(Instant.now());
        recipe.setOrderPosition(orderPosition);
        secondRecipe = new Recipe();
        secondRecipe.setDescription("recipe");
        secondRecipe.setQuantity(5);
        secondRecipe.setProduct(product);
        secondRecipe.setValidThru(Instant.now());
        secondRecipe.setOrderPosition(secondOrderPosition);
        thirdRecipe = new Recipe();
        thirdRecipe.setDescription("recipe");
        thirdRecipe.setQuantity(6);
        thirdRecipe.setProduct(product);
        thirdRecipe.setValidThru(Instant.now());
        thirdRecipe.setOrderPosition(thirdOrderPosition);
    }

    @Test
    void removeShouldRemoveProductFromCollection() {
        recipeDao.add(recipe);

        Recipe actualRecipe = recipeDao.getByOrderPositionId(1)
                .get();

        assertEquals(recipe, actualRecipe);
    }

    @Test
    void addShouldAddRecipeToCollection() {
        long id = recipeDao.add(recipe);

        long sizeAfterAdd = recipeDao.getAll()
                .size();
        assertEquals(1, sizeAfterAdd);
        assertEquals(1, id);
    }

    @Test
    void getShouldGetRecipeFromCollection() {
        long id = recipeDao.add(recipe);

        Recipe acquiredRecipe = recipeDao.get(id)
                .get();

        assertEquals(recipe, acquiredRecipe);
    }

    @Test
    void getAllShouldGetAllRecipesFromCollection() {
        recipeDao.add(recipe);
        recipeDao.add(secondRecipe);
        recipeDao.add(thirdRecipe);

        Collection<Recipe> acquiredRecipes = recipeDao.getAll();

        Collection<Recipe> expectedRecipes = new ArrayList<>();
        expectedRecipes.add(recipe);
        expectedRecipes.add(secondRecipe);
        expectedRecipes.add(thirdRecipe);
        assertEquals(expectedRecipes, acquiredRecipes);
    }


    @Test
    void removeShouldRemoveRecipeFromCollection() {
        recipeDao.add(recipe);

        recipeDao.remove(1);
        long sizeAfterRemove = recipeDao.getAll()
                .size();

        assertEquals(0, sizeAfterRemove);
    }
}
