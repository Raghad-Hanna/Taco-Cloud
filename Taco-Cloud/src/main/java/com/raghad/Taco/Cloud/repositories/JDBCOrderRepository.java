package com.raghad.Taco.Cloud.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

import com.raghad.Taco.Cloud.models.Order;
import com.raghad.Taco.Cloud.models.Taco;

@Repository
public class JDBCOrderRepository implements OrderRepository {
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JDBCOrderRepository(JdbcTemplate jdbcTemplate) {
        this.orderInserter = new SimpleJdbcInsert(jdbcTemplate)
        .withTableName("Taco_Order").usingGeneratedKeyColumns("id");

        this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
        .withTableName("Tac_Order_Tacos");
        
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(LocalDateTime.now());
        long orderId = this.saveBasicOrderDetails(order);
        order.setId(orderId);

        for(Taco taco: order.getTacos())
            this.saveTacoToOrder(taco, orderId);

        return order;
    }

    private long saveBasicOrderDetails(Order order) {
        Map<String, Object> values = this.objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt());

        long orderId = this.orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());

        this.orderTacoInserter.execute(values);
    }
}
