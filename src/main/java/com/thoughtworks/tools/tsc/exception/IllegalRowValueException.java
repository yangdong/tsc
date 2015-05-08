package com.thoughtworks.tools.tsc.exception;

public class IllegalRowValueException extends RuntimeException{
    public IllegalRowValueException() {
    }

    public IllegalRowValueException(String message) {
        super(message);
    }

    public IllegalRowValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
