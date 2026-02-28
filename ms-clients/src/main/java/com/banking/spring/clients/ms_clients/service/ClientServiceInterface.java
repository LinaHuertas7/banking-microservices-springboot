package com.banking.spring.clients.ms_clients.service;

import java.util.List;

import com.banking.spring.clients.ms_clients.DTO.request.ClientRequestDTO;
import com.banking.spring.clients.ms_clients.DTO.request.ClientUpdateDTO;
import com.banking.spring.clients.ms_clients.DTO.response.ClientResponseDTO;

public interface ClientServiceInterface {
    ClientResponseDTO create(ClientRequestDTO request);

    ClientResponseDTO findBySlug(String slug);

    List<ClientResponseDTO> findAll();

    ClientResponseDTO replace(String slug, ClientRequestDTO request);

    ClientResponseDTO update(String slug, ClientUpdateDTO request);

    void delete(String slug);
}
