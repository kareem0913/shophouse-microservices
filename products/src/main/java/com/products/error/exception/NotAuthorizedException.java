package com.products.error.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class NotAuthorizedException extends RuntimeException{
    int code = 403;
    String description;

    public NotAuthorizedException(String message, String description) {
        super(message);
        this.description = description;
    }

    public NotAuthorizedException(String message, Throwable cause, String description) {
        super(message, cause);
        this.description = description;
    }
}
