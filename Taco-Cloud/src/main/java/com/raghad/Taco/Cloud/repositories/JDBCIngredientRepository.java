package com.raghad.Taco.Cloud.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.raghad.Taco.Cloud.models.Ingredient;

@Repository
public class JDBCIngredientRepository implements IngredientRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return this.jdbcTemplate.query("SELECT id, name, type FROM Ingredient",
                this::mapRowToIngredient);
    }

    @Override
    public Ingredient findOne(String id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM Ingredient WHERE id=?",
                this::mapRowToIngredient, id);
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        this.jdbcTemplate.update("INSERT INTO Ingredient (id, name, type) VALUES " +
                "(?, ?, ?)", ingredient.getId(),
                ingredient.getName(), ingredient.getType().toString());

        return ingredient;
    }
}
