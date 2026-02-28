package com.banking.spring.clients.ms_clients.controller;

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

import com.banking.spring.clients.ms_clients.DTO.request.ClientRequestDTO;
import com.banking.spring.clients.ms_clients.DTO.request.ClientUpdateDTO;
import com.banking.spring.clients.ms_clients.DTO.response.ClientResponseDTO;
import com.banking.spring.clients.ms_clients.service.ClientServiceInterface;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientServiceInterface clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(request));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ClientResponseDTO> findBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(clientService.findBySlug(slug));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @PutMapping("/{slug}")
    public ResponseEntity<ClientResponseDTO> replace(@PathVariable String slug,
            @Valid @RequestBody ClientRequestDTO request) {
        return ResponseEntity.ok(clientService.replace(slug, request));
    }

    @PatchMapping("/{slug}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable String slug,
            @Valid @RequestBody ClientUpdateDTO request) {
        return ResponseEntity.ok(clientService.update(slug, request));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<String> delete(@PathVariable String slug) {
        clientService.delete(slug);
        return ResponseEntity.ok("Cliente eliminado exitosamente");
    }
}
