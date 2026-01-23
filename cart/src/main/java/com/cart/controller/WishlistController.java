package com.cart.controller;

import com.cart.model.dto.wishlist.WishlistResponse;
import com.cart.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping
    public Page<WishlistResponse> httpGetWishlistItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("user-id") Long userId
    ){
        Pageable pageable = PageRequest.of(page, size);
        return wishlistService.wishlistItems(userId, pageable);
    }

    @GetMapping("/add/{id}")
    public void httpAddItem(
            @PathVariable Long id,
            @RequestHeader("user-id") Long userId
    ){
        wishlistService.addItem(id, userId);
    }

    @DeleteMapping("/{id}")
    public void httpDeleteItem(
            @PathVariable Long id
    ){
        wishlistService.deleteItem(id);
    }
}
