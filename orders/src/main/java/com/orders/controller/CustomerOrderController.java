package com.orders.controller;

import com.orders.model.dto.order.OrderResponse;
import com.orders.service.customer.CustomerOrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Customer Order", description = "Customer Order API")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    @PostMapping
    public OrderResponse httpCreateOrder(@RequestHeader("user-id") Long userId) {
        return customerOrderService.placeOrder(userId);
    }

    @GetMapping
    public Page<OrderResponse> httpGetUserOrders(@RequestHeader("user-id") Long userId,
                                                     Pageable pageable) {
        return customerOrderService.getUserOrders(userId, pageable);
    }

    @GetMapping("/{orderId}")
    public OrderResponse httpGetOrderById(@PathVariable Long orderId,
                                          @RequestHeader("user-id") Long userId) {
        return customerOrderService.getOrderById(orderId, userId);
    }
}
