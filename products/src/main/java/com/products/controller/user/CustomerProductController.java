package com.products.controller.user;

import com.products.model.dto.product.ProductResponse;
import com.products.service.customer.product.CustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CustomerProductController {
    private final CustomerProductService customerProductService;

    @GetMapping
    Page<ProductResponse> httpGetAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return customerProductService.findAllProducts(pageable);
    }

    @GetMapping("/{id}")
    ProductResponse httpGetProductById(@PathVariable Long id) {
        return customerProductService.findProduct(id);
    }

    @GetMapping("/category/{categoryId}")
    Page<ProductResponse> httpGetProductsByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return customerProductService.findProductsByCategoryId(categoryId, pageable);
    }

}
