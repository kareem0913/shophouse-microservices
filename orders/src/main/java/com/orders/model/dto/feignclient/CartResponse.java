package com.orders.model.dto.feignclient;

import com.orders.model.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long id;
    private Long productId;
    private String name;
    private Double price;
    private Double discount;
    private DiscountType discountType;
    private Integer quantity;
    private Set<String> images;
}
