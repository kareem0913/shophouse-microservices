package com.orders.service.admin.impl;

import com.orders.error.exception.ResourceNotFoundException;
import com.orders.mapper.OrderMapper;
import com.orders.model.dto.order.OrderResponse;
import com.orders.model.entity.Order;
import com.orders.model.enums.OrderStatus;
import com.orders.repository.OrderRepository;
import com.orders.service.admin.AdminOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        log.debug("Fetching all orders with pagination: {}", pageable);

        Page<Order> orders = orderRepository.findAllOrderByCreatedAtDesc(pageable);
        return orders.map(orderMapper::toOrderResponse);
    }

    @Override
    public OrderResponse getOrder(Long orderId) {
        log.debug("Fetching order with id: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found",
                        "Order not found with id: " + orderId
                ));

        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional
    public void changeOrderStatus(Long orderId, OrderStatus orderStatus) {
        log.info("Changing order status for order id: {} to status: {}", orderId, orderStatus);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found",
                        "Order not found with id: " + orderId
                ));

        OrderStatus previousStatus = order.getOrderStatus();
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order status changed successfully for order id: {} from {} to {}",
                orderId, previousStatus, orderStatus);
    }
}
