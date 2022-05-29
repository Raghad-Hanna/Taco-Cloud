package com.raghad.Taco.Cloud.models;

import lombok.NoArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Ingredient {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Type type;

    public Ingredient(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
