package com.banking.spring.ms_accounts.exception;

public class ClientServiceUnavailableException extends RuntimeException {
    public ClientServiceUnavailableException(String message) {
        super(message, null, true, false);
    }
}
