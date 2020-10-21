package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectionBasedRecipeDaoTest {

    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedRecipeDao recipeDao;

    private OperationPosition operationPosition;
    private OperationPosition secondOperationPosition;
    private OperationPosition thirdOperationPosition;
    private Recipe recipe;
    private Recipe secondRecipe;
    private Recipe thirdRecipe;
    private Product product;

    @BeforeEach
    void set() {
        product = new Product(1, "product", new BigDecimal(123), null, true);
        operationPosition = new OperationPosition(1, 2, product, null);
        secondOperationPosition = new OperationPosition(2, 5, product, null);
        thirdOperationPosition = new OperationPosition(3, 7, product, null);
        recipe = new Recipe(-1, "recipe", 2, product, Instant.now(), operationPosition);
        secondRecipe = new Recipe(-1, "recipe", 5, product, Instant.now(), secondOperationPosition);
        thirdRecipe = new Recipe(-1, "recipe", 6, product, Instant.now(), thirdOperationPosition);
    }

    @Test
    void removeShouldRemoveProductFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        recipeDao.add(recipe);

        Recipe actualRecipe = recipeDao.getByOperationPositionId(1)
                .get();

        Assertions.assertEquals(recipe, actualRecipe);
    }

    @Test
    void addShouldAddRecipeToCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);

        long id = recipeDao.add(recipe);

        long sizeAfterAdd = recipeDao.getAll()
                .size();
        Assertions.assertEquals(1, sizeAfterAdd);
        verify(idGenerator, times(1)).getNextId();
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldGetRecipeFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        long id = recipeDao.add(recipe);

        Recipe acquiredRecipe = recipeDao.get(id)
                .get();

        Assertions.assertEquals(recipe, acquiredRecipe);
    }

    @Test
    void getAllShouldGetAllRecipesFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        recipeDao.add(recipe);
        recipeDao.add(secondRecipe);
        recipeDao.add(thirdRecipe);

        Collection<Recipe> acquiredRecipes = recipeDao.getAll();

        Collection<Recipe> expectedRecipes = new ArrayList<>();
        expectedRecipes.add(recipe);
        expectedRecipes.add(secondRecipe);
        expectedRecipes.add(thirdRecipe);
        Assertions.assertEquals(expectedRecipes, acquiredRecipes);
    }


    @Test
    void removeShouldRemoveRecipeFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        recipeDao.add(recipe);

        recipeDao.remove(0);
        long sizeAfterRemove = recipeDao.getAll()
                .size();

        Assertions.assertEquals(0, sizeAfterRemove);
    }
}

