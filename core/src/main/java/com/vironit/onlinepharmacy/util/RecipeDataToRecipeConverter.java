package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.RecipeDto;
import com.vironit.onlinepharmacy.model.Recipe;
import org.springframework.stereotype.Component;

@Component
public class RecipeDataToRecipeConverter implements Converter<Recipe, RecipeDto> {
    @Override
    public Recipe convert(RecipeDto recipeDto) {
        Recipe recipe=new Recipe();
        recipe.setDescription(recipeDto.getDescription());
        recipe.setValidThru(recipeDto.getValidThru());
        recipe.setQuantity(recipeDto.getQuantity());
        return recipe;
    }
}
