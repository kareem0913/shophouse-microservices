package com.products.repository;

import com.products.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @EntityGraph(value = "Product.detail", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findById(Long id);

    @Override
    @EntityGraph(value = "Product.detail", type = EntityGraph.EntityGraphType.LOAD)
    Page<Product> findAll(Pageable pageable);

    @EntityGraph(value = "Product.detail", type = EntityGraph.EntityGraphType.LOAD)
    Page<Product> findByCategoriesId(Long categoryId, Pageable pageable);

    @EntityGraph(value = "Product.detail", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findByIdAndStatusTrue(Long id);

    @EntityGraph(value = "Product.detail", type = EntityGraph.EntityGraphType.LOAD)
    Page<Product> findAllByStatusTrue(Pageable pageable);

    @EntityGraph(value = "Product.detail", type = EntityGraph.EntityGraphType.LOAD)
    Page<Product> findAllByCategoriesIdAndStatusTrue(Long categoryId, Pageable pageable);

    @Modifying
    @Query("UPDATE Product p SET p.status = :status WHERE p.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") boolean status);

    @Query("SELECT p FROM Product p WHERE p.id IN :productIds")
    @EntityGraph(value = "Product.detail", type = EntityGraph.EntityGraphType.LOAD)
    List<Product> findAllByIdIn(@Param("productIds") Set<Long> productIds);

}
