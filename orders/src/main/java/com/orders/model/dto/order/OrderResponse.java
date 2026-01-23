package com.orders.model.dto.order;

import com.orders.model.dto.feignclient.UserProfileResponse;
import com.orders.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Double totalAmount;
    private Double totalDiscount;
    private OrderStatus orderStatus;
    private UserProfileResponse user;
    private List<OrderItemResponse> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
