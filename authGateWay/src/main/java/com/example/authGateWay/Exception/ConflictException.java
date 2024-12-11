package com.example.authGateWay.Exception;

public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
