package com.products.service.admin.category;

import com.products.model.dto.category.CategoryCreate;
import com.products.model.dto.category.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminCategoryService {

    CategoryResponse findCategory(Long id);

    Page<CategoryResponse> findAllCategories(Pageable pageable);

    CategoryResponse createCategory(CategoryCreate categoryCreate);

    CategoryResponse updateCategory(Long id, CategoryCreate categoryCreate);

    Page<CategoryResponse> findCategoriesByParentId(Long parentId, Pageable pageable);

    void changeCategoryStatus(Long id, boolean status);

    void deleteCategory(Long id);

}
