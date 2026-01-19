package com.cart.service;

import com.cart.model.dto.cart.CartRequest;
import com.cart.model.dto.cart.CartResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {
    Page<CartResponse> cartItems(Long userId, Pageable pageable);
    void addItem(CartRequest cartRequest, Long userId);
    void deleteItem(Long id);
}
