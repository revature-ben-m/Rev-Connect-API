package com.rev_connect_api.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.PAYLOAD_TOO_LARGE)
public class BioTextTooLongException extends RuntimeException {
    private String message;
    public BioTextTooLongException() {

    }

    public BioTextTooLongException(String message) {
        super(message);
        this.message = message;
    }
}

