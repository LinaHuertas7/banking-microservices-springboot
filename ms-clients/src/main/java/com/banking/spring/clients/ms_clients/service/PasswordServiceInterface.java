package com.banking.spring.clients.ms_clients.service;

public interface PasswordServiceInterface {
    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
