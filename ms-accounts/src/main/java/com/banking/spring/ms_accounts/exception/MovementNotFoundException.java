package com.banking.spring.ms_accounts.exception;

public class MovementNotFoundException extends RuntimeException {
    public MovementNotFoundException(String message) {
        super(message, null, true, false);
    }
}
