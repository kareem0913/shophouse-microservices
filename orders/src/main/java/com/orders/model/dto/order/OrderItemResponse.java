package com.orders.model.dto.order;

import com.orders.model.dto.product.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Integer quantity;
    private Double priceAtTime;
    private ProductResponse product;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
