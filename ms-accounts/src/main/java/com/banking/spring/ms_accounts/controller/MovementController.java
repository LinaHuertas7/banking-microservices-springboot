package com.banking.spring.ms_accounts.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.spring.ms_accounts.DTO.request.MovementRequestDTO;
import com.banking.spring.ms_accounts.DTO.response.MovementResponseDTO;
import com.banking.spring.ms_accounts.service.MovementServiceInterface;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovementController {
    private final MovementServiceInterface movementService;

    @PostMapping
    public ResponseEntity<MovementResponseDTO> create(@Valid @RequestBody MovementRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movementService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<MovementResponseDTO>> findAll() {
        return ResponseEntity.ok(movementService.findAll());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<MovementResponseDTO> findBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(movementService.findBySlug(slug));
    }
}
