package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Recipe;

import java.util.Optional;

public interface RecipeDao extends Dao<Recipe> {
    Optional<Recipe> getByOperationPositionId(long id);
}
