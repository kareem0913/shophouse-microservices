package com.gatewayserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.properties")
public class AppProperties {
    private String jwtSecret;
    private long jwtExpiration;
}
