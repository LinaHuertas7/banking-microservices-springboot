package com.banking.spring.ms_accounts.exception;

public class ClientNotExistExeption extends RuntimeException {
    public ClientNotExistExeption(String message) {
        super(message, null, true, false);
    }
}