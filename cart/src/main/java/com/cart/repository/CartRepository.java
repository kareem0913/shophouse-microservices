package com.cart.repository;

import com.cart.model.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    Cart findByUserIdAndProductId(Long userId, Long productId);
    List<Cart> findByUserId(Long userId);
}
