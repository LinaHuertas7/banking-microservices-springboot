package com.banking.spring.ms_accounts.DTO.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.banking.spring.ms_accounts.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportResponseDTO {
    @JsonFormat(pattern = "d/M/yyyy")
    LocalDateTime date;

    String clientName;
    String accountNumber;
    AccountType accountType;
    BigDecimal initialBalance;
    Boolean status;
    BigDecimal availableBalance;
    List<MovementResponseDTO> movements;
}