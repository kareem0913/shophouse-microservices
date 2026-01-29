package com.orders.service.client.fallback;

import com.orders.error.exception.ServiceUnavailableException;
import com.orders.model.dto.feignclient.CartResponse;
import com.orders.service.client.CartFeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartFallback implements CartFeignClient {
    @Override
    public List<CartResponse> httpGetCartItems(Long userId) {
        throw new ServiceUnavailableException("cart service", "an error happen please tray after some time");
    }
}
