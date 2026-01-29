package com.cart.service.client;

import com.cart.error.exception.ServiceUnavailableException;
import com.cart.model.dto.feignclient.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductFallback implements ProductFeignClient{
    @Override
    public ProductResponse httpGetProductById(Long id) {
        throw new ServiceUnavailableException("product service", "an error happen please tray after some time");
    }
}
