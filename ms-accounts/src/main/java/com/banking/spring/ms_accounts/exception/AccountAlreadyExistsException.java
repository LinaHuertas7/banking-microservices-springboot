package com.banking.spring.ms_accounts.exception;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String message) {
        super(message, null, true, false);
    }
}
