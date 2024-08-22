package com.rev_connect_api.exceptions;

/**
 * Custom exception thrown when a requested resource is not found.
 * This exception extends {@link RuntimeException}, allowing it to be used
 * as an unchecked exception in the application.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code ResourceNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
