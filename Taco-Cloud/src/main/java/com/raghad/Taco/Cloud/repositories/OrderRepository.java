package com.raghad.Taco.Cloud.repositories;

import com.raghad.Taco.Cloud.models.Order;

public interface OrderRepository {
    Order save(Order order);
}
