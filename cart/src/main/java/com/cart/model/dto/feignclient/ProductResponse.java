package com.cart.model.dto.feignclient;

import com.cart.model.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private double discount;
    private double finalPrice;
    private double finalDiscount;
    private DiscountType discountType;
    private Integer quantity;
    private boolean status;
    private Set<String> imagesUrls;
    private Set<CategoryResponse> categories;
}
