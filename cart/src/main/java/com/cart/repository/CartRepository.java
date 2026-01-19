package com.cart.repository;

import com.cart.model.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    Cart findByUserIdAndProductId(Long userId, Long productId);
    Page<Cart> findByUserId(Long userId, Pageable pageable);
}
