package com.vironit.onlinepharmacy.service.recipe;

import com.vironit.onlinepharmacy.dao.RecipeDao;
import com.vironit.onlinepharmacy.dto.RecipeDto;
import com.vironit.onlinepharmacy.model.OrderPosition;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.Recipe;
import com.vironit.onlinepharmacy.service.exception.RecipeServiceException;
import com.vironit.onlinepharmacy.util.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicRecipeService implements RecipeService {

    private final RecipeDao recipeDao;
    private final Converter<Recipe, RecipeDto> recipeDataToRecipeConverter;

    public BasicRecipeService(RecipeDao recipeDao, Converter<Recipe, RecipeDto> recipeDataToRecipeConverter) {
        this.recipeDao = recipeDao;
        this.recipeDataToRecipeConverter = recipeDataToRecipeConverter;
    }

    @Override
    public long add(RecipeDto recipeDto) {
        Product product = new Product();
        product.setId(recipeDto.getProductId());
        OrderPosition orderPosition=new OrderPosition();
        orderPosition.setId(recipeDto.getOrderPositionId());
        Recipe recipe= recipeDataToRecipeConverter.convert(recipeDto);
        recipe.setOrderPosition(orderPosition);
        recipe.setProduct(product);
        return recipeDao.add(recipe);
    }

    @Override
    public Recipe get(long id) {
        return recipeDao.get(id)
                .orElseThrow(() -> new RecipeServiceException("Can't get recipe. Recipe with id " + id + " not found."));
    }

    @Override
    public Collection<Recipe> getAll() {
        return recipeDao.getAll();
    }

    @Override
    public void remove(long id) {
        recipeDao.remove(id);
    }
}
