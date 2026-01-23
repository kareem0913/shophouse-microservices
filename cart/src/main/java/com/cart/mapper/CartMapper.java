package com.cart.mapper;

import com.cart.model.dto.cart.CartRequest;
import com.cart.model.dto.cart.CartResponse;
import com.cart.model.dto.feignclient.ProductResponse;
import com.cart.model.entity.Cart;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CartMapper {
    public static CartResponse toCartResponse(Cart cart, ProductResponse product) {
        return CartResponse.builder()
                .id(cart.getId())
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .discountType(product.getDiscountType())
                .quantity(cart.getQuantity())
                .images(product.getImagesUrls())
                .build();
    }

    public static  Cart toCart(CartRequest cartRequest, Long userId) {
        return new Cart(userId, cartRequest.getProductId(), cartRequest.getQuantity());
    }
}
