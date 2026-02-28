package com.banking.spring.ms_accounts.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banking.spring.ms_accounts.DTO.response.ReportResponseDTO;
import com.banking.spring.ms_accounts.exception.InvalidDateRangeException;
import com.banking.spring.ms_accounts.service.ReportServiceInterface;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
@Validated
public class ReportController {

    private final ReportServiceInterface reportService;

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<ReportResponseDTO>> getAccountStatement(
            @PathVariable Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        if (fechaInicio.isAfter(fechaFin)) {
            throw new InvalidDateRangeException("Fecha inicio no puede ser posterior a fecha fin");
        }

        return ResponseEntity.ok(reportService.getAccountStatement(clienteId, fechaInicio, fechaFin));
    }
}
