package com.orders.service.customer;

import com.orders.model.dto.order.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerOrderService {

    OrderResponse placeOrder(Long userId);

    Page<OrderResponse> getUserOrders(Long userId, Pageable pageable);

    OrderResponse getOrderById(Long orderId, Long userId);
}
