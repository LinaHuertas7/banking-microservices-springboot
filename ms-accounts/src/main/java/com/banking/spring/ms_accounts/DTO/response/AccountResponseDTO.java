package com.banking.spring.ms_accounts.DTO.response;

import java.math.BigDecimal;
import java.util.List;

import com.banking.spring.ms_accounts.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@With
public class AccountResponseDTO {
    private String slug;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal initialBalance;
    private BigDecimal availableBalance;
    private Boolean status;
    private Long clientId;
    private List<MovementResponseDTO> movements;
}
