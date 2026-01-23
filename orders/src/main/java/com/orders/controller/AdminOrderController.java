package com.orders.controller;

import com.orders.model.dto.order.OrderResponse;
import com.orders.model.enums.OrderStatus;
import com.orders.service.admin.AdminOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@SecurityRequirement(name = "bearer-key")
public class AdminOrderController {
    private final AdminOrderService adminOrderService;

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieve all orders with pagination")
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        Page<OrderResponse> orders = adminOrderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID", description = "Retrieve a specific order by its ID")
    public ResponseEntity<OrderResponse> getOrder(
             @PathVariable Long orderId) {
        OrderResponse order = adminOrderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/{orderId}/status")
    @Operation(summary = "Change order status", description = "Update the status of a specific order")
    public ResponseEntity<Void> changeOrderStatus(
            @PathVariable Long orderId,
             @RequestParam OrderStatus status) {
        adminOrderService.changeOrderStatus(orderId, status);
        return ResponseEntity.noContent().build();
    }
}
