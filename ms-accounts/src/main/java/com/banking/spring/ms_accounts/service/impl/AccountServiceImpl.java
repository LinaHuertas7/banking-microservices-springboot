package com.banking.spring.ms_accounts.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.banking.spring.ms_accounts.DTO.request.AccountRequestDTO;
import com.banking.spring.ms_accounts.DTO.request.AccountUpdateDTO;
import com.banking.spring.ms_accounts.DTO.response.AccountResponseDTO;
import com.banking.spring.ms_accounts.exception.AccountAlreadyExistsException;
import com.banking.spring.ms_accounts.exception.AccountNotFoundException;
import com.banking.spring.ms_accounts.mapper.AccountMapperInterface;
import com.banking.spring.ms_accounts.model.Account;
import com.banking.spring.ms_accounts.repository.AccountRepositoryInterface;
import com.banking.spring.ms_accounts.repository.MovementRepositoryInterface;
import com.banking.spring.ms_accounts.service.AccountServiceInterface;
import com.banking.spring.ms_accounts.service.ClienteQueryServiceInterface;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountServiceInterface {

    private final AccountRepositoryInterface accountRepository;
    private final AccountMapperInterface accountMapper;

    private final MovementRepositoryInterface movementRepository;
    private final ClienteQueryServiceInterface clienteQueryService;

    @Override
    @Transactional
    public AccountResponseDTO create(AccountRequestDTO request) {
        clienteQueryService.validateClient(request.getClientId());

        if (accountRepository.existsByAccountNumberAndDeletedAtIsNull(request.getAccountNumber())) {
            throw new AccountAlreadyExistsException(
                    "Ya existe una cuenta con el número %s".formatted(request.getAccountNumber()));
        }

        Account saved = accountRepository.save(accountMapper.toEntity(request));
        log.info("Cuenta {} creada con slug: {}", saved.getAccountNumber(), saved.getSlug());

        return accountMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDTO findBySlug(String slug) {
        Account account = accountRepository.findActiveBySlugWithMovements(slug)
                .orElseThrow(() -> new AccountNotFoundException(
                        "No se encontró la cuenta con slug %s".formatted(slug)));
        return accountMapper.toResponse(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDTO> findAll() {
        List<AccountResponseDTO> accounts = accountRepository.findAllActive()
                .stream()
                .map(accountMapper::toResponse)
                .toList();

        log.info("Cuentas {} encontradas", accounts.size());

        return accounts;
    }

    @Override
    @Transactional
    public AccountResponseDTO replace(String slug, AccountRequestDTO request) {
        Account account = findActiveAccountBySlug(slug);

        boolean accountNumberTaken = accountRepository
                .existsByAccountNumberAndDeletedAtIsNullAndSlugNot(request.getAccountNumber(), slug);

        if (accountNumberTaken) {
            throw new AccountAlreadyExistsException(
                    "El número de cuenta %s ya está en uso".formatted(request.getAccountNumber()));
        }

        accountMapper.replaceEntityFromDto(request, account);
        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountResponseDTO update(String slug, AccountUpdateDTO request) {
        log.info("Actualizando cuenta con slug: {}", slug);

        Account account = findActiveAccountBySlug(slug);
        accountMapper.updateEntityFromDto(request, account);

        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    @Transactional
    public void delete(String slug) {
        Account account = findActiveAccountBySlug(slug);

        movementRepository.softDeleteByAccountId(account.getAccountId(), LocalDateTime.now());

        account.anonymize();
        accountRepository.save(account);

        log.info("Cuenta {} eliminada junto con todos sus movimientos", account.getAccountNumber());
    }

    public Account findActiveAccountBySlug(String slug) {
        return accountRepository.findActiveBySlug(slug).orElseThrow(
                () -> new AccountNotFoundException("No se encontró la cuenta con slug %s".formatted(slug)));
    }
}