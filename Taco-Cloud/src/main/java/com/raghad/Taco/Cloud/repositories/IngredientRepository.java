package com.raghad.Taco.Cloud.repositories;

import com.raghad.Taco.Cloud.models.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
