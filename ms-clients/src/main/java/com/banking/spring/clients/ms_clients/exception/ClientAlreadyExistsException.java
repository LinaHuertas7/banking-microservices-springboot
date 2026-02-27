package com.banking.spring.clients.ms_clients.exception;

public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}
