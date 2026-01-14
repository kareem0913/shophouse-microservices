package com.files.error.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class FileStorageException extends RuntimeException{
    int code = 409;
    String description;

    public FileStorageException(String message, String description) {
        super(message);
        this.description = description;
    }
}
