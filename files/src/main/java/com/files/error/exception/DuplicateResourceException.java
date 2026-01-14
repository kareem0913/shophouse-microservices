package com.files.error.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class DuplicateResourceException extends RuntimeException{
    int code = 409;
    String description;

    public DuplicateResourceException(String message, String description) {
        super(message);
        this.description = description;
    }

    public DuplicateResourceException(String message, Throwable cause, String description) {
        super(message, cause);
        this.description = description;
    }
}
