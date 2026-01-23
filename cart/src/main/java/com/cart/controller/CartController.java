package com.cart.controller;

import com.cart.model.dto.cart.CartRequest;
import com.cart.model.dto.cart.CartResponse;
import com.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user-cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public List<CartResponse> httpGetCartItems(
            @RequestHeader("user-id") Long userId
            ){
        return cartService.cartItems(userId);
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
