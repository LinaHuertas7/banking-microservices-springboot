package com.banking.spring.clients.ms_clients.service;

import java.util.List;

import com.banking.spring.clients.ms_clients.DTO.request.ClientRequestDTO;
import com.banking.spring.clients.ms_clients.DTO.request.ClientUpdateDTO;
import com.banking.spring.clients.ms_clients.DTO.response.ClientResponseDTO;

public interface ClientServiceInterface {
    ClientResponseDTO create(ClientRequestDTO request);

    ClientResponseDTO findById(Long id);

    List<ClientResponseDTO> findAll();

    ClientResponseDTO replace(Long id, ClientRequestDTO request);

    ClientResponseDTO update(Long id, ClientUpdateDTO request);

    void delete(Long id);
}
