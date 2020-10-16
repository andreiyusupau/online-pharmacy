package com.vironit.onlinepharmacy.service.recipe;

import com.vironit.onlinepharmacy.model.Recipe;
import com.vironit.onlinepharmacy.service.CRUDService;

//TODO:implement service
public interface RecipeService extends CRUDService<Recipe, Recipe, Recipe> {

    void validateRecipe();
}
