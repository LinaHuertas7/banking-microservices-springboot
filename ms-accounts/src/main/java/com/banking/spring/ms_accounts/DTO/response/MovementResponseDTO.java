package com.banking.spring.ms_accounts.DTO.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.banking.spring.ms_accounts.enums.MovementType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementResponseDTO {
    private String slug;
    private LocalDateTime date;
    private MovementType movementType;
    private BigDecimal amount;
    private BigDecimal balance;
    private String accountSlug;
    private String accountNumber;
}
