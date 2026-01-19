package com.cart.model.dto.cart;

import com.cart.model.enums.DiscountType;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CartResponse {
    private Long id;
    private String name;
    private Double price;
    private Double discount;
    private DiscountType discountType;
    private Integer quantity;
    private Set<String> images;
}
