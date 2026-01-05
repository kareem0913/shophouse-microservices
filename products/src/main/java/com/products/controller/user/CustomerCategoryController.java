package com.products.controller.user;

import com.products.model.dto.category.CategoryResponse;
import com.products.service.customer.category.CustomerCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CustomerCategoryController {

    private final CustomerCategoryService customerCategoryService;

    @GetMapping
    public Page<CategoryResponse> httpGetCategories(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerCategoryService.findAllCategories(pageable);
    }

    @GetMapping("/{id}")
    public CategoryResponse httpGetCategoryById(@PathVariable Long id) {
        return customerCategoryService.findCategory(id);
    }

    @GetMapping("/parent/{parentId}")
    public Page<CategoryResponse> httpGetCategoriesByParentId(@PathVariable Long parentId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerCategoryService.findCategoriesByParentId(parentId, pageable);
    }
}
