package com.orders.service.client;

import com.orders.model.dto.product.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("products")
public interface ProductFeignClient {
    @GetMapping("/{id}")
    ProductResponse httpGetProductById(@PathVariable Long id);
}
