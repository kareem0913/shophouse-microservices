package com.cart.service.client;

import com.cart.model.dto.feignclient.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products", fallback =  ProductFallback.class)
public interface ProductFeignClient {
    @GetMapping("/{id}")
    ProductResponse httpGetProductById(@PathVariable Long id);
}
