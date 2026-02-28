package com.banking.spring.ms_accounts.service;

import java.util.List;

import com.banking.spring.ms_accounts.DTO.request.MovementRequestDTO;
import com.banking.spring.ms_accounts.DTO.request.MovementUpdateDTO;
import com.banking.spring.ms_accounts.DTO.response.MovementResponseDTO;

public interface MovementServiceInterface {
    MovementResponseDTO create(MovementRequestDTO request);

    MovementResponseDTO findById(Long id);

    List<MovementResponseDTO> findAll();

    MovementResponseDTO update(Long id, MovementUpdateDTO request);

    void delete(Long id);
}
