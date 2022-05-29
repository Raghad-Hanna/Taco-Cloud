package com.raghad.Taco.Cloud.models;

import lombok.RequiredArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Data
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "taco_ingredients", joinColumns = @JoinColumn(name = "taco_id"),
                                          inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void saveCreationDate() {
        this.createdAt = LocalDateTime.now();
    }
}
