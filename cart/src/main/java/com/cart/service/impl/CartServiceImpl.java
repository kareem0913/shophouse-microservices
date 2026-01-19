package com.cart.service.impl;

import com.cart.error.exception.ResourceNotFoundException;
import com.cart.model.dto.cart.CartRequest;
import com.cart.model.dto.cart.CartResponse;
import com.cart.model.dto.feignclient.ProductResponse;
import com.cart.model.entity.Cart;
import com.cart.repository.CartRepository;
import com.cart.service.CartService;
import com.cart.service.client.ProductFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.cart.mapper.CartMapper.toCart;
import static com.cart.mapper.CartMapper.toCartResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductFeignClient productFeignClient;
    @Override
    public Page<CartResponse> cartItems(final Long userId, Pageable pageable) {
        return cartRepository.findByUserId(userId, pageable)
                .map(item -> {
                    ProductResponse productResponse =  productFeignClient.httpGetProductById(item.getProductId());
                    return toCartResponse(item, productResponse);
                });
    }

    @Override
    public void addItem(CartRequest cartRequest, Long userId) {
        Long productId = cartRequest.getProductId();
        if(cartRepository.existsByUserIdAndProductId(userId, productId)){
            Cart existingItem = cartRepository.findByUserIdAndProductId(userId, productId);
            existingItem.setQuantity(cartRequest.getQuantity());
            cartRepository.save(existingItem);
            return;
        }
        cartRepository.save(toCart(cartRequest, userId));
    }

    @Override
    public void deleteItem(Long id) {
        Cart cartItem = cartRepository.findById(id).orElseThrow(() -> {
            log.error("cart item with ID {} not found", id);
            return new ResourceNotFoundException("cart item not found",
                    "No cart item found with the provided ID: " + id);
        });
        cartRepository.delete(cartItem);
    }
}
