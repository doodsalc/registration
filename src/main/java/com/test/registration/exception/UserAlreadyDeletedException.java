package com.test.registration.exception;

public class UserAlreadyDeletedException extends Exception {
    public UserAlreadyDeletedException() {
    }
    public UserAlreadyDeletedException(String message) {
        super(message);
    }

    public UserAlreadyDeletedException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}