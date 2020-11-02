package com.vironit.onlinepharmacy.service.recipe;

import com.vironit.onlinepharmacy.dao.RecipeDao;
import com.vironit.onlinepharmacy.dto.RecipeData;
import com.vironit.onlinepharmacy.model.OrderPosition;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.Recipe;
import com.vironit.onlinepharmacy.service.exception.RecipeServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@Disabled
@ExtendWith(MockitoExtension.class)
public class BasicRecipeServiceTest {

    @Mock
    private RecipeDao recipeDao;
    @InjectMocks
    private BasicRecipeService recipeService;

    private OrderPosition orderPosition;
    private OrderPosition secondOrderPosition;
    private Product product;
    private Recipe recipe;
    private Recipe secondRecipe;
    private RecipeData recipeData;
    private Collection<Recipe> recipes;

    @BeforeEach
    void set() {
        product = new Product(3, "thirdProduct", new BigDecimal("67"), null, false);
        orderPosition = new OrderPosition(1, 7, product, null);
        secondOrderPosition = new OrderPosition(2, 6, product, null);
        recipe = new Recipe(1, "description", 2, product, Instant.now(), orderPosition);
        secondRecipe = new Recipe(2, "second description", 6, product, Instant.now(), secondOrderPosition);
        recipeData=new RecipeData("testdescr",2,1,Instant.now(),1);
        recipes = new ArrayList<>();
        recipes.add(recipe);
        recipes.add(secondRecipe);
    }

    @Test
    void addShouldUseDao() {
        when(recipeDao.add(any()))
                .thenReturn(0L);

        long id = recipeService.add(recipeData);

        verify(recipeDao).add(recipe);
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldNotThrowException() {
        when(recipeDao.get(anyLong()))
                .thenReturn(Optional.of(recipe));

        Recipe actualRecipe = recipeService.get(1);

        verify(recipeDao).get(1);
        Assertions.assertEquals(recipe, actualRecipe);
    }

    @Test
    void getShouldThrowException() {
        when(recipeDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(RecipeServiceException.class, () -> recipeService.get(1));

        verify(recipeDao).get(1);
        String expectedMessage = "Can't get recipe. Recipe with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAllShouldNotThrowException() {
        when(recipeDao.getAll())
                .thenReturn(recipes);

        Collection<Recipe> actualRecipes = recipeService.getAll();

        Assertions.assertEquals(recipes, actualRecipes);
    }

    @Test
    void removeShouldUseDao() {
        when(recipeDao.remove(anyLong()))
                .thenReturn(true);

        recipeService.remove(1);

        verify(recipeDao).remove(1);
    }
}
