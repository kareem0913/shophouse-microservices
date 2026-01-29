package com.cart.error.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ServiceUnavailableException extends RuntimeException {
    int code = 503;
    String description;

    public ServiceUnavailableException(String message, String description){
        super(message);
        this.description = description;
    }
}
