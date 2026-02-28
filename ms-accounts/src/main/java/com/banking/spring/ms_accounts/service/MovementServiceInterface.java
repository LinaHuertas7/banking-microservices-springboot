package com.banking.spring.ms_accounts.service;

import java.util.List;

import com.banking.spring.ms_accounts.DTO.request.MovementRequestDTO;
import com.banking.spring.ms_accounts.DTO.response.MovementResponseDTO;

public interface MovementServiceInterface {
    MovementResponseDTO create(MovementRequestDTO request);

    MovementResponseDTO findBySlug(String slug);

    List<MovementResponseDTO> findAll();

    void delete(String slug);
}
