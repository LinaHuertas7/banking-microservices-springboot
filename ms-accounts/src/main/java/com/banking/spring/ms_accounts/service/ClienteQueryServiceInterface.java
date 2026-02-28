package com.banking.spring.ms_accounts.service;

import com.banking.spring.ms_accounts.DTO.response.ClientQueryResponseDTO;

public interface ClienteQueryServiceInterface {

    ClientQueryResponseDTO validateClient(Long clientId);
}
