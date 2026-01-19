package com.cart.mapper;

import com.cart.model.dto.feignclient.ProductResponse;
import com.cart.model.dto.wishlist.WishlistResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WishlistMapper {
    public static WishlistResponse toWishlistResponse(Long wishlistId, ProductResponse product) {
        return WishlistResponse.builder()
                .id(wishlistId)
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .discountType(product.getDiscountType())
                .images(product.getImagesUrls())
                .build();
    }
}
