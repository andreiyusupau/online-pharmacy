package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.model.Recipe;

//TODO:implement service
public interface RecipeService extends CRUDService<Recipe, Recipe, Recipe> {

    void validateRecipe();
}
