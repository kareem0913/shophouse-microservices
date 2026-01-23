package com.orders.service.client;

import com.orders.model.dto.feignclient.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient("cart")
public interface CartFeignClient {
    @GetMapping("user-cart")
    List<CartResponse> httpGetCartItems(@RequestHeader("user-id") Long userId);
}
