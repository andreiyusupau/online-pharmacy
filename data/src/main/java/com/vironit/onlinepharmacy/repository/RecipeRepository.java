package com.vironit.onlinepharmacy.repository;

import com.vironit.onlinepharmacy.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
}
