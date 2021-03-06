package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.RecipeDao;
import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.Recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.dao.jpa.JpaRecipeDao}
 */
@Deprecated
public class CollectionBasedRecipeDao implements RecipeDao {

    private final IdGenerator idGenerator;
    private final Collection<Recipe> recipeList = new ArrayList<>();

    public CollectionBasedRecipeDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(Recipe recipe) {
        long id = idGenerator.getNextId();
        recipe.setId(id);
        boolean successfulAdd = recipeList.add(recipe);
        return successfulAdd ? id : -1L;
    }

    @Override
    public Optional<Recipe> get(long id) {
        return recipeList.stream()
                .filter(recipe -> recipe.getId() == id)
                .findFirst();
    }

    @Override
    public Collection<Recipe> getAll() {
        return recipeList;
    }

    @Override
    public boolean remove(long id) {
        return recipeList.removeIf(recipe -> recipe.getId() == id);
    }


    @Override
    public Optional<Recipe> getByOrderPositionId(long id) {
        return recipeList.stream()
                .filter(recipe -> recipe.getOrderPosition()
                        .getId() == id)
                .findFirst();
    }
}
