package com.example.demo1.exception;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String message) {
        super(message);
    }
}
