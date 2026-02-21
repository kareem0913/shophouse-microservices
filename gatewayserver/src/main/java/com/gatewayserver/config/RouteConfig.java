package com.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.http.HttpMethod;

import java.time.Duration;

@Configuration
public class RouteConfig {

    private static final String FALLBACK_URI = "forward:/fallback";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(predicate ->
                        predicate.path("/users/**")
                        .filters(filter ->
                                filter.rewritePath("/users/(?<segment>.*)","/${segment}")
                                        .circuitBreaker(config ->
                                        config.setName("userCircuitBreaker")
                                                .setFallbackUri(FALLBACK_URI))
                                        .retry(retryConfig -> retryConfig.setRetries(3)
                                                .setMethods(HttpMethod.GET)
                                                .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))

                        )
                        .uri("lb://USERS"))
                .route(predicate ->
                        predicate.path("/products/**")
                                .filters(filter ->
                                        filter.rewritePath("/products/(?<segment>.*)","/${segment}")
                                                .circuitBreaker(config ->
                                                        config.setName("productCircuitBreaker")
                                                                .setFallbackUri(FALLBACK_URI)
                                                )
                                                .retry(retryConfig -> retryConfig.setRetries(3)
                                                        .setMethods(HttpMethod.GET)
                                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                                )
                                .uri("lb://PRODUCTS"))
                .build();
    }
}
