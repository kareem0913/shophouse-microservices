package com.gatewayserver.filter;

import com.gatewayserver.util.FilterUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import static com.gatewayserver.util.FilterUtility.getCorrelationId;

@Configuration
@Slf4j
public class ResponseTraceFilter {
    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) ->
             chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String correlationId = getCorrelationId(requestHeaders);
                log.debug("Updated the correlation id to the outbound headers: {}", correlationId);
                exchange.getResponse().getHeaders().add(FilterUtility.CORRELATION_ID, correlationId);
            }));
    }
}
