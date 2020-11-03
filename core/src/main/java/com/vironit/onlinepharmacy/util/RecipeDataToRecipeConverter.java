package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.RecipeData;
import com.vironit.onlinepharmacy.model.Recipe;
import org.springframework.stereotype.Component;

@Component
public class RecipeDataToRecipeConverter implements Converter<Recipe, RecipeData> {
    @Override
    public Recipe convert(RecipeData recipeData) {
        Recipe recipe=new Recipe();
        recipe.setDescription(recipeData.getDescription());
        recipe.setValidThru(recipeData.getValidThru());
        recipe.setQuantity(recipeData.getQuantity());
        return recipe;
    }
}
