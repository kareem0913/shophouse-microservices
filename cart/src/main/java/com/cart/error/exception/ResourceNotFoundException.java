package com.cart.error.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ResourceNotFoundException extends RuntimeException{
    int code = 404;
    String description;

    public ResourceNotFoundException(String message, String description) {
        super(message);
        this.description = description;
    }

    public ResourceNotFoundException(String message, Throwable cause, String description) {
        super(message, cause);
        this.description = description;
    }
}
