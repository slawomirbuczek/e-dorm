package com.edorm.exceptions.users;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Entered password does not match");
    }
}
