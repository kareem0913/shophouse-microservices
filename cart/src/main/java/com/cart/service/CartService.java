package com.cart.service;

import com.cart.model.dto.cart.CartRequest;
import com.cart.model.dto.cart.CartResponse;

import java.util.List;

public interface CartService {
    List<CartResponse> cartItems(Long userId);
    void addItem(CartRequest cartRequest, Long userId);
    void deleteItem(Long id);
}
