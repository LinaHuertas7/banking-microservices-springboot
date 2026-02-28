package com.banking.spring.ms_accounts.exception;

public class InvalidDateRangeException extends RuntimeException {
    public InvalidDateRangeException(String message) {
        super(message, null, true, false);
    }
}