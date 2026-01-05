package com.products.service.customer.category;

import com.products.model.dto.category.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerCategoryService{

    CategoryResponse findCategory(Long id);

    Page<CategoryResponse> findAllCategories(Pageable pageable);

    Page<CategoryResponse> findCategoriesByParentId(Long parentId, Pageable pageable);
}
