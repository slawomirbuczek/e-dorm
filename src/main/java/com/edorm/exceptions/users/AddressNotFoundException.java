package com.edorm.exceptions.users;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(long id) {
        super("Address with id: " + id + " not found");
    }
}