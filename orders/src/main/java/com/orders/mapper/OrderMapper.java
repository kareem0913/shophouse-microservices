package com.orders.mapper;

import com.orders.model.dto.feignclient.UserProfileResponse;
import com.orders.model.dto.order.OrderItemResponse;
import com.orders.model.dto.order.OrderResponse;
import com.orders.model.dto.product.ProductResponse;
import com.orders.model.entity.Order;
import com.orders.model.entity.OrderItem;
import com.orders.service.client.ProductFeignClient;
import com.orders.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final ProductFeignClient productFeignClient;
    private final UserFeignClient userFeignClient;

    public OrderResponse toOrderResponse(Order order){
        UserProfileResponse user = userFeignClient.httpGetCurrentUserProfile(order.getUserId());
        return OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .totalDiscount(order.getTotalDiscount())
                .orderStatus(order.getOrderStatus())
                .user(user)
                .orderItems(toOrderItemResponse(order.getOrderItems()))
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    private List<OrderItemResponse> toOrderItemResponse(List<OrderItem> orderItems){
        return orderItems.stream()
                .map(orderItem -> {
                    ProductResponse productResponse = productFeignClient.httpGetProductById(orderItem.getProductId());
                    return OrderItemResponse.builder()
                            .id(orderItem.getId())
                            .quantity(orderItem.getQuantity())
                            .priceAtTime(orderItem.getPriceAtTime())
                            .product(productResponse)
                            .createdAt(orderItem.getCreatedAt())
                            .updatedAt(orderItem.getUpdatedAt())
                            .build();
                })
                .toList();
    }
}
