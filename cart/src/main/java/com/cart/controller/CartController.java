package com.cart.controller;

import com.cart.model.dto.cart.CartRequest;
import com.cart.model.dto.cart.CartResponse;
import com.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user-cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public Page<CartResponse> httpGetCartCartItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("user-id") Long userId
            ){
        Pageable pageable = PageRequest.of(page, size);
        return cartService.cartItems(userId, pageable);
    }

    @PostMapping
    public void httpAddItem(
            @Valid @RequestBody CartRequest cartRequest,
            @RequestHeader("user-id") Long userId
    ){
        cartService.addItem(cartRequest, userId);
    }

    @DeleteMapping("/{id}")
    public void httpDeleteItem(
            @PathVariable Long id
    ){
        cartService.deleteItem(id);
    }
}
