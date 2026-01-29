package com.orders.service.client.fallback;

import com.orders.error.exception.ServiceUnavailableException;
import com.orders.model.dto.product.ProductResponse;
import com.orders.service.client.ProductFeignClient;
import org.springframework.stereotype.Component;

@Component
public class ProductFallback implements ProductFeignClient {
    @Override
    public ProductResponse httpGetProductById(Long id) {
        throw new ServiceUnavailableException("product service", "an error happen please tray after some time");
    }
}
