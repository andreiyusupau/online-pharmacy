package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.RecipeDto;
import com.vironit.onlinepharmacy.model.Recipe;
import com.vironit.onlinepharmacy.service.recipe.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public Collection<Recipe> getAll() {
        return recipeService.getAll();
    }

    @PostMapping
    public long add(@RequestBody RecipeDto recipeDto) {
        return recipeService.add(recipeDto);
    }

    @GetMapping("/{id}")
    public Recipe get(@PathVariable Long id) {
        return recipeService.get(id);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        recipeService.remove(id);
    }

}