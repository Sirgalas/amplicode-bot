package ru.sergalas.amplicodebot.exception;

import java.util.NoSuchElementException;

public class UserSessionNotFoundException extends NoSuchElementException {
    public UserSessionNotFoundException(String message) {
        super(message);
    }
    public UserSessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserSessionNotFoundException(Throwable cause) {
        super(cause);
    }
}
