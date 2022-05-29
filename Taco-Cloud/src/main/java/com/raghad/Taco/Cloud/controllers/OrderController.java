package com.raghad.Taco.Cloud.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import javax.validation.Valid;
import java.util.List;

import com.raghad.Taco.Cloud.repositories.JPAOrderRepository;
import com.raghad.Taco.Cloud.configurations.configuration_properties_holders.PaginationProperties;
import com.raghad.Taco.Cloud.models.Order;
import com.raghad.Taco.Cloud.models.User;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
@Slf4j
public class OrderController {
    private JPAOrderRepository orderRepository;
    private PaginationProperties paginationProperties;

    @Autowired
    public OrderController(JPAOrderRepository orderRepository, PaginationProperties paginationProperties) {
        this.orderRepository = orderRepository;
        this.paginationProperties = paginationProperties;
    }

    @GetMapping("/current")
    public String getOrderForm() {
        return "order_form";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors,
                               SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if(errors.hasErrors())
            return "order_form";

        log.info("Order Submitted: " + order);
        order.setUser(user);
        this.orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/home";
    }

    @GetMapping("/recent")
    public String getRecentUserOrders(@AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(this.paginationProperties.getPageNumber(),
                this.paginationProperties.getPageSize());

        List<Order> orders = this.orderRepository.findByUserOrderByPlacedAtDesc(user, pageable);
        model.addAttribute("orders", orders);

        return "recent_user_orders";
    }
}
