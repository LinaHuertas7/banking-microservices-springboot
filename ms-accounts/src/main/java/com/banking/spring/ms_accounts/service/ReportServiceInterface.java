package com.banking.spring.ms_accounts.service;

import java.time.LocalDate;
import java.util.List;

import com.banking.spring.ms_accounts.DTO.response.ReportResponseDTO;

public interface ReportServiceInterface {

    List<ReportResponseDTO> getAccountStatement(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin);
}