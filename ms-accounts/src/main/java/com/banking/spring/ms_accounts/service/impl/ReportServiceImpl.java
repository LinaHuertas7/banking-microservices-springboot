package com.banking.spring.ms_accounts.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.spring.ms_accounts.DTO.response.ClientQueryResponseDTO;
import com.banking.spring.ms_accounts.DTO.response.ReportResponseDTO;
import com.banking.spring.ms_accounts.mapper.ReportMapperInterface;
import com.banking.spring.ms_accounts.model.Account;
import com.banking.spring.ms_accounts.model.Movement;
import com.banking.spring.ms_accounts.repository.MovementRepositoryInterface;
import com.banking.spring.ms_accounts.service.ClienteQueryServiceInterface;
import com.banking.spring.ms_accounts.service.ReportServiceInterface;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportServiceInterface {

    private final MovementRepositoryInterface movementRepository;
    private final ClienteQueryServiceInterface clienteQueryService;
    private final ReportMapperInterface reportMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ReportResponseDTO> getAccountStatement(
            Long clienteId,
            LocalDate fechaInicio,
            LocalDate fechaFin) {

        LocalDateTime start = fechaInicio.atStartOfDay();
        LocalDateTime end = fechaFin.atTime(LocalTime.MAX);

        ClientQueryResponseDTO client = clienteQueryService.validateClient(clienteId);

        log.info("Generando estado de cuenta para cliente: {} entre {} y {}",
                client.getClientName(), fechaInicio, fechaFin);

        List<Movement> movements = movementRepository
                .findByClientIdAndDateRange(clienteId, start, end);

        Map<Long, List<Movement>> movementsByAccountId = movements.stream()
                .collect(Collectors.groupingBy(m -> m.getAccount().getAccountId()));

        return movementsByAccountId.values().stream()
                .map(group -> buildAccountReport(group, client.getClientName()))
                .collect(Collectors.toList());
    }

    private ReportResponseDTO buildAccountReport(List<Movement> movements, String clientName) {
        Account account = movements.get(0).getAccount();

        LocalDateTime date = movements.stream()
                .map(Movement::getDate)
                .max(LocalDateTime::compareTo)
                .orElseThrow();

        return new ReportResponseDTO(
                date,
                clientName,
                account.getAccountNumber(),
                account.getAccountType(),
                account.getInitialBalance(),
                account.getStatus(),
                account.getAvailableBalance(),
                reportMapper.toMovementResponseList(movements));
    }
}
