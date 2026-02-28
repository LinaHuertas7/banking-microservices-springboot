package com.banking.spring.ms_accounts.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message, null, true, false);
    }
}