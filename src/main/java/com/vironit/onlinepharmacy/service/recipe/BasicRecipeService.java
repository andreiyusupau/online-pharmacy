package com.vironit.onlinepharmacy.service.recipe;

import com.vironit.onlinepharmacy.dao.RecipeDao;
import com.vironit.onlinepharmacy.model.Recipe;
import com.vironit.onlinepharmacy.service.exception.RecipeServiceException;

import java.util.Collection;

public class BasicRecipeService implements RecipeService {

    private final RecipeDao recipeDao;

    public BasicRecipeService(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    @Override
    public long add(Recipe recipe) {

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
