package com.banking.spring.ms_accounts.service;

import java.util.List;

import com.banking.spring.ms_accounts.DTO.request.AccountRequestDTO;
import com.banking.spring.ms_accounts.DTO.request.AccountUpdateDTO;
import com.banking.spring.ms_accounts.DTO.response.AccountResponseDTO;

public interface AccountServiceInterface {
    AccountResponseDTO create(AccountRequestDTO request);

    AccountResponseDTO findBySlug(String slug);

    List<AccountResponseDTO> findAll();

    AccountResponseDTO replace(String slug, AccountRequestDTO request);

    AccountResponseDTO update(String slug, AccountUpdateDTO request);

    void delete(String slug);
}
