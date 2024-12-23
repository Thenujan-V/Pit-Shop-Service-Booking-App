package com.example.booking.Exception;

public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
    public NoContentException(String message, Throwable cause) {
        super(message, cause);
    }
}