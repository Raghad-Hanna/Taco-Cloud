package com.raghad.Taco.Cloud.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.raghad.Taco.Cloud.repositories.JPATacoRepository;
import com.raghad.Taco.Cloud.repositories.JPAIngredientRepository;
import com.raghad.Taco.Cloud.models.Taco;
import com.raghad.Taco.Cloud.models.Ingredient;
import com.raghad.Taco.Cloud.models.Order;

@Controller
@RequestMapping(path = "/tacos")
@SessionAttributes("order")
@Slf4j
public class TacoController {
    private JPATacoRepository tacoRepository;
    private JPAIngredientRepository ingredientRepository;

    @Autowired
    public TacoController(JPATacoRepository tacoRepository, JPAIngredientRepository ingredientRepository) {
        this.tacoRepository = tacoRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String getTacoDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        this.ingredientRepository.findAll().forEach(i -> ingredients.add(i));

        Ingredient.Type[] types = Ingredient.Type.values();

        for(Ingredient.Type type: types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        model.addAttribute("design", new Taco());
        return "taco_design_form";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.getType().toString().equals(type.toString()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processTacoDesign(Taco taco, @ModelAttribute Order order) {
        log.info("Processing Taco: " + taco);
        Taco savedTaco = this.tacoRepository.save(taco);
        order.getTacos().add(savedTaco);

        return "redirect:/orders/current";
    }
}
