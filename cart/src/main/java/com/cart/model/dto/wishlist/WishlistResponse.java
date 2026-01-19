package com.cart.model.dto.wishlist;

import com.cart.model.enums.DiscountType;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class WishlistResponse {
    private Long id;
    private Long productId;
    private String name;
    private Double price;
    private Double discount;
    private DiscountType discountType;
    private Set<String> images;
}
