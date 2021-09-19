package com.edorm.exceptions.users;

public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String email) {
        super("User with email: " + email + " already exists");
    }
}
