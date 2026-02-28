package com.banking.spring.ms_accounts.DTO.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.banking.spring.ms_accounts.enums.MovementType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovementResponseDTO {
    private Long movementId;
    private LocalDateTime date;
    private MovementType movementType;
    private BigDecimal amount;
    private BigDecimal balance;
    private Long accountId;
    private String accountNumber;
}
