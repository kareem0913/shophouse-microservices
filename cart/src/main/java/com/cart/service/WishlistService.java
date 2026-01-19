package com.cart.service;

import com.cart.model.dto.wishlist.WishlistResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistService {
    Page<WishlistResponse> wishlistItems(Long userId, Pageable pageable);
    void addItem(Long productId, Long userId);
    void deleteItem(Long id);
}
