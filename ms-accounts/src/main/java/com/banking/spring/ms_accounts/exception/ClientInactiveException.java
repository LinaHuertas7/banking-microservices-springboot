package com.banking.spring.ms_accounts.exception;

public class ClientInactiveException extends RuntimeException {
    public ClientInactiveException(String message) {
        super(message, null, true, false);
    }
}