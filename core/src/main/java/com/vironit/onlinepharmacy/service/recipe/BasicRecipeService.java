package com.vironit.onlinepharmacy.service.recipe;

import com.vironit.onlinepharmacy.dto.RecipeDto;
import com.vironit.onlinepharmacy.model.OrderPosition;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.Recipe;
import com.vironit.onlinepharmacy.repository.RecipeRepository;
import com.vironit.onlinepharmacy.service.exception.RecipeServiceException;
import com.vironit.onlinepharmacy.util.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicRecipeService implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final Converter<Recipe, RecipeDto> recipeDataToRecipeConverter;

    public BasicRecipeService(RecipeRepository recipeRepository, Converter<Recipe, RecipeDto> recipeDataToRecipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeDataToRecipeConverter = recipeDataToRecipeConverter;
    }

    @Override
    public long add(RecipeDto recipeDto) {
        Product product = new Product();
        product.setId(recipeDto.getProductId());
        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setId(recipeDto.getOrderPositionId());
        Recipe recipe = recipeDataToRecipeConverter.convert(recipeDto);
        recipe.setOrderPosition(orderPosition);
        recipe.setProduct(product);
        return recipeRepository.save(recipe)
                .getId();
    }

    @Override
    public Recipe get(long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeServiceException("Can't get recipe. Recipe with id " + id + " not found."));
    }

    @Override
    public Collection<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    @Override
    public void remove(long id) {
        recipeRepository.deleteById(id);
    }
}
