package com.rev_connect_api.validations;

import com.rev_connect_api.models.User;

public class ConnectionRequestValidator {

    public static void validate(User requester, User recipient) {
        if (requester.equals(recipient)) {
            throw new IllegalArgumentException("You cannot send a connection request to yourself");
        }
    }
}
