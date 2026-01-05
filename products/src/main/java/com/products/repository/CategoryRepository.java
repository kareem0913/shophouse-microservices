package com.products.repository;

import com.products.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Boolean existsByName(String name);
    Page<Category> findAllByParentId(Long parentId, Pageable pageable);

    @Modifying
    @Query("UPDATE Category c SET c.status = :status WHERE c.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") boolean status);

    Optional<Category> findByIdAndStatusTrue(Long id);

    Page<Category> findAllByStatusTrue(Pageable pageable);

    Page<Category> findAllByParentIdAndStatusTrue(Long parentId, Pageable pageable);

}
