package com.gatewayserver.filter;

import com.gatewayserver.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
@Order(-1)
@RequiredArgsConstructor
public class AdminAuthorizationFilter implements GlobalFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (path.matches(".*/admin(/.*)?")) {

            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return unauthorized(exchange);
            }

            String token = authHeader.substring(7);

            if (!jwtTokenProvider.validateToken(token)
                    || !isAdmin(jwtTokenProvider.getUserRolesFromToken(token))) {

                log.info("Admin Authorization Filter - access denied");
                return unauthorized(exchange);
            }
        }

        return chain.filter(exchange);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

    private boolean isAdmin(List<String> roles) {
        return roles.contains("ADMIN");
    }
}
