package com.thoughtworks.tools.tsc.exception;

public class SheetNotExistException extends Exception{

    public SheetNotExistException() {
    }

    public SheetNotExistException(String message) {
        super(message);
    }

    public SheetNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
