package com.products.controller.admin;

import com.products.model.dto.product.ProductCreate;
import com.products.model.dto.product.ProductResponse;
import com.products.service.admin.product.AdminProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @GetMapping
    public Page<ProductResponse> httpGetAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return adminProductService.findAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ProductResponse httpGetProductById(@PathVariable Long id) {
        return adminProductService.findProduct(id);
    }

    @GetMapping("/category/{categoryId}")
    public Page<ProductResponse> httpGetProductsByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return adminProductService.findProductsByCategoryId(categoryId, pageable);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponse httpCreateProduct(@Valid @NotNull @ModelAttribute ProductCreate productCreate) {
        return adminProductService.createProduct(productCreate);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponse httpUpdateProduct(
            @PathVariable Long id,
            @Valid @NotNull @ModelAttribute ProductCreate productCreate){
        return adminProductService.updateProduct(id, productCreate);
    }

    @PutMapping("/{id}/status")
    public void httpChangeProductStatus(@PathVariable Long id, @RequestParam boolean status) {
        adminProductService.changeProductStatus(id, status);
    }
    @DeleteMapping("/{id}")
    public void httpDeleteProduct(@PathVariable Long id) {
        adminProductService.deleteProduct(id);
    }
}
