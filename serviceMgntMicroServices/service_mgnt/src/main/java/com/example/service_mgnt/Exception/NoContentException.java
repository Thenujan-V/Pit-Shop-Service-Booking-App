package com.example.service_mgnt.Exception;

public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
    public NoContentException(String message, Throwable cause) {
        super(message, cause);
    }
}