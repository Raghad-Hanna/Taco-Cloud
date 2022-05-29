package com.raghad.Taco.Cloud.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.sql.Types;

import com.raghad.Taco.Cloud.models.Taco;
import com.raghad.Taco.Cloud.models.Ingredient;

@Repository
public class JDBCTacoRepository implements TacoRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCTacoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Taco save(Taco taco) {
        taco.setCreatedAt(LocalDateTime.now());
        long tacoId = this.saveBasicIngredientDetails(taco);
        taco.setId(tacoId);

        for(Ingredient ingredient: taco.getIngredients())
            this.saveIngredientToTaco(ingredient, tacoId);

        return taco;
    }

    private long saveBasicIngredientDetails(Taco taco) {
        PreparedStatementCreator statement =
                new PreparedStatementCreatorFactory("INSERT INTO Taco (name, createdAt) " +
                        "VALUES (?, ?)", Types.VARCHAR, Types.TIMESTAMP)
                        .newPreparedStatementCreator(Arrays.asList(taco.getName(), taco.getCreatedAt()));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(statement, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, Long tacoId) {
        this.jdbcTemplate.update("INSERT INTO Taco_Ingredients (taco, ingredient) VALUES " +
                "(?, ?)", ingredient.getId(), tacoId);
    }
}
