package com.vesko.application.exception;

import static java.lang.String.format;

public class ResourceNotFoundException extends JsonParsableException {
    public ResourceNotFoundException(Object key, Object value) {
        super(format("Resource not found by field %s with value %s", key, value));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
