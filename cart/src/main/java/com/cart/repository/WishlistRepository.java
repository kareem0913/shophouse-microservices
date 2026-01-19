package com.cart.repository;

import com.cart.model.entity.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    Page<Wishlist> findByUserId(Long userId, Pageable pageable);

}
