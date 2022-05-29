package com.raghad.Taco.Cloud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.raghad.Taco.Cloud.models.Ingredient;

public interface JPAIngredientRepository extends CrudRepository<Ingredient, String> {
}
