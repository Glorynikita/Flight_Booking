package com.example.demo1.exception;

public class TicketNotFound extends RuntimeException {
    public TicketNotFound(String message) {
        super(message);
    }
}
