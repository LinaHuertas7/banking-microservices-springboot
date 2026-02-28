package com.banking.spring.ms_accounts.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.spring.ms_accounts.DTO.request.AccountRequestDTO;
import com.banking.spring.ms_accounts.DTO.request.AccountUpdateDTO;
import com.banking.spring.ms_accounts.DTO.response.AccountResponseDTO;
import com.banking.spring.ms_accounts.service.AccountServiceInterface;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountServiceInterface accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@Valid @RequestBody AccountRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> findAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<AccountResponseDTO> findBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(accountService.findBySlug(slug));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<AccountResponseDTO> replace(
            @PathVariable String slug,
            @Valid @RequestBody AccountRequestDTO request) {
        return ResponseEntity.ok(accountService.replace(slug, request));
    }

    @PatchMapping("/{slug}")
    public ResponseEntity<AccountResponseDTO> update(
            @PathVariable String slug,
            @Valid @RequestBody AccountUpdateDTO request) {
        return ResponseEntity.ok(accountService.update(slug, request));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<String> delete(@PathVariable String slug) {
        accountService.delete(slug);
        return ResponseEntity.ok("Cuenta eliminada exitosamente");
    }
}
