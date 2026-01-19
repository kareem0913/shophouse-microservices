package com.cart.service.impl;

import com.cart.error.exception.DuplicateResourceException;
import com.cart.error.exception.ResourceNotFoundException;
import com.cart.model.dto.feignclient.ProductResponse;
import com.cart.model.dto.wishlist.WishlistResponse;
import com.cart.model.entity.Wishlist;
import com.cart.repository.WishlistRepository;
import com.cart.service.WishlistService;
import com.cart.service.client.ProductFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.cart.mapper.WishlistMapper.toWishlistResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductFeignClient productFeignClient;

    @Override
    public Page<WishlistResponse> wishlistItems(Long userId, Pageable pageable) {
        return wishlistRepository.findByUserId(userId, pageable)
                .map(item -> {
                    ProductResponse productResponse =  productFeignClient.httpGetProductById(item.getProductId());
                    return toWishlistResponse(item.getId(), productResponse);
                });
    }

    @Override
    public void addItem(Long productId, Long userId) {
        if(wishlistRepository.existsByUserIdAndProductId(userId, productId)){
            throw new DuplicateResourceException("Wishlist item already exists", "Wishlist item already exists");
        }
        wishlistRepository.save(new Wishlist(userId, productId));
    }

    @Override
    public void deleteItem(Long id) {
        Wishlist wishlistItem = wishlistRepository.findById(id).orElseThrow(() -> {
            log.error("wishlist item with ID {} not found", id);
            return new ResourceNotFoundException("wishlist item not found",
                    "No cart item found with the provided ID: " + id);
        });
        wishlistRepository.delete(wishlistItem);
    }
}
