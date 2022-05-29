package com.raghad.Taco.Cloud.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

import com.raghad.Taco.Cloud.models.Order;
import com.raghad.Taco.Cloud.models.User;

public interface JPAOrderRepository extends PagingAndSortingRepository<Order, Long> {
    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
