package com.rev_connect_api.exceptions;

/**
 * Custom exception thrown when an action is attempted by a user
 * who does not have the necessary authorization.
 * This exception extends {@link RuntimeException}, making it an unchecked exception.
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Constructs a new {@code UnauthorizedException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
