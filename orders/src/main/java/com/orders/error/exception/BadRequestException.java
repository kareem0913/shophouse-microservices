package com.orders.error.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends RuntimeException {

    int code = 400;
    String description;

    public BadRequestException(String message, String description) {
        super(message);
        this.description = description;
    }
}
