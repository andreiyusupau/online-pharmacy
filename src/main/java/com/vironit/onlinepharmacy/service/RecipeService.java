package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.model.Recipe;

public interface RecipeService extends CRUDService<Recipe> {

void validateRecipe();
}
