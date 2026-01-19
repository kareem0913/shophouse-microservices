package com.gatewayserver.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.server.ServerWebExchange;

import org.springframework.http.HttpHeaders;
import java.util.List;

@UtilityClass
public class FilterUtility {

    public static  ServerWebExchange setUserId(ServerWebExchange exchange, String userId) {
        return exchange.mutate().request(exchange.getRequest().mutate().header("user-id", userId).build()).build();
    }
}
