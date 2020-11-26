package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Recipe;

import java.util.Optional;
/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.RecipeRepository}
 */
@Deprecated
public interface RecipeDao extends ImmutableDao<Recipe> {
    Optional<Recipe> getByOrderPositionId(long id);
}
