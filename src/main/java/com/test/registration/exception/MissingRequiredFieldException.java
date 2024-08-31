package com.test.registration.exception;

public class MissingRequiredFieldException extends Exception {
    public MissingRequiredFieldException() {

    }

    public MissingRequiredFieldException(String message) {
        super(message);
    }

    public MissingRequiredFieldException(Throwable cause) {
        super(cause);
    }

    public MissingRequiredFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
