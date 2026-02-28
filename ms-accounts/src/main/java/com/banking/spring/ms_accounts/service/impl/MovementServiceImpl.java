package com.banking.spring.ms_accounts.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banking.spring.ms_accounts.DTO.request.MovementRequestDTO;
import com.banking.spring.ms_accounts.DTO.response.MovementResponseDTO;
import com.banking.spring.ms_accounts.exception.MovementNotFoundException;
import com.banking.spring.ms_accounts.mapper.MovementMapperInterface;
import com.banking.spring.ms_accounts.model.Account;
import com.banking.spring.ms_accounts.model.Movement;
import com.banking.spring.ms_accounts.repository.MovementRepositoryInterface;
import com.banking.spring.ms_accounts.service.AccountServiceInterface;
import com.banking.spring.ms_accounts.service.MovementServiceInterface;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementServiceInterface {
    private final MovementRepositoryInterface movementRepository;
    private final MovementMapperInterface movementMapper;
    private final AccountServiceInterface accountService;

    @Override
    @Transactional
    public MovementResponseDTO create(MovementRequestDTO request) {
        Account account = accountService.findActiveAccountBySlug(request.getAccountSlug());
        Movement movement = account.applyMovement(request.getAmount());
        movementRepository.save(movement);

        log.info("Movimiento {} registrado en cuenta {} con slug: {}. Nuevo saldo: {}",
                movement.getMovementType(), account.getAccountNumber(),
                movement.getSlug(), movement.getBalance());

        return movementMapper.toResponse(movement);
    }

    @Override
    @Transactional(readOnly = true)
    public MovementResponseDTO findBySlug(String slug) {
        return movementMapper.toResponse(findActiveMovementBySlug(slug));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovementResponseDTO> findAll() {
        List<MovementResponseDTO> movements = movementRepository.findAllActive()
                .stream()
                .map(movementMapper::toResponse)
                .toList();

        log.info("Movimientos {} encontradas", movements.size());

        return movements;
    }

    @Override
    @Transactional
    public void delete(String slug) {
        Movement movement = findActiveMovementBySlug(slug);

        movement.anonymize();
        movementRepository.save(movement);

        log.info("Movimiento {} eliminado", movement.getSlug());
    }

    private Movement findActiveMovementBySlug(String slug) {
        return movementRepository.findActiveBySlug(slug)
                .orElseThrow(() -> new MovementNotFoundException(
                        "No se encontró el movimiento con slug %s".formatted(slug)));
    }
}
