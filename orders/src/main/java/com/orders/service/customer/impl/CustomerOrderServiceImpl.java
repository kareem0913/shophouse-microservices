package com.orders.service.customer.impl;

import com.orders.error.exception.ResourceNotFoundException;
import com.orders.mapper.OrderMapper;
import com.orders.model.dto.feignclient.CartResponse;
import com.orders.model.enums.DiscountType;
import com.orders.model.enums.OrderStatus;
import com.orders.service.client.CartFeignClient;
import com.orders.service.customer.CustomerOrderService;
import com.orders.model.dto.order.OrderResponse;
import com.orders.model.entity.Order;
import com.orders.model.entity.OrderItem;
import com.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {
    private final OrderRepository orderRepository;
    private final CartFeignClient cartFeignClient;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse placeOrder(Long userId) {
        List<CartResponse> cart = cartFeignClient.httpGetCartItems(userId);
        Order order = new Order();
        Map<String, Object> extracted = calculateCartTotals(cart, order);
        order.setTotalAmount((Double) extracted.get("totalAmount"));
        order.setTotalDiscount((Double) extracted.get("totalDiscount"));
        order.setOrderStatus(OrderStatus.PENDING);
        order.setUserId(userId);
        order.setOrderItems((List<OrderItem>) extracted.get("orderItems"));

        orderRepository.save(order);
        log.info("Order placed successfully");
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public Page<OrderResponse> getUserOrders(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(orderMapper::toOrderResponse);
    }

    @Override
    public OrderResponse getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found", "Order not found with id: " + orderId));
        return orderMapper.toOrderResponse(order);
    }

    private static Map<String, Object> calculateCartTotals(List<CartResponse> cart, Order order) {
        double totalAmount = 0.0;
        double totalDiscount = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartResponse item : cart) {
            double itemTotal = item.getPrice() * item.getQuantity();
            if (item.getDiscountType() == DiscountType.AMOUNT) {
                totalDiscount += item.getDiscount();
                totalAmount += (itemTotal - item.getDiscount());
            } else if (item.getDiscountType() == DiscountType.PERCENTAGE) {
                double discount = itemTotal * item.getDiscount() / 100;
                totalDiscount += discount;
                totalAmount += (itemTotal - discount);
            } else {
                totalAmount += itemTotal;
            }

            // create orderItems
            OrderItem orderItem = OrderItem.builder()
                    .quantity(item.getQuantity())
                    .priceAtTime(item.getPrice())
                    .productId(item.getProductId())
                    .order(order)
                    .build();
            orderItems.add(orderItem);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        result.put("totalDiscount", totalDiscount);
        result.put("orderItems", orderItems);
        return result;
    }

}
