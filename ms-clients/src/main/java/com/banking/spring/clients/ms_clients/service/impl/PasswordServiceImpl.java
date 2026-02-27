package com.banking.spring.clients.ms_clients.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.spring.clients.ms_clients.service.PasswordServiceInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordServiceInterface {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
