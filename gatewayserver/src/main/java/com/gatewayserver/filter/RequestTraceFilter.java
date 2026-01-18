package com.gatewayserver.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.gatewayserver.util.FilterUtility.getCorrelationId;
import static com.gatewayserver.util.FilterUtility.setCorrelationId;

@Order(1)
@Component
@Slf4j
public class RequestTraceFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(requestHeaders)) {
            log.debug("correlation-id found in RequestTraceFilter : {}",
                    getCorrelationId(requestHeaders));
        } else {
            String correlationID = generateCorrelationId();
            exchange = setCorrelationId(exchange, correlationID);
            log.debug("correlation-id generated in RequestTraceFilter : {}", correlationID);
        }
        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        return getCorrelationId(requestHeaders) != null;
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

}
