package com.edorm.exceptions.users;

public class TheSamePasswordException extends RuntimeException {
    public TheSamePasswordException() {
        super("New password is the same as the old password");
    }

}
