package com.banking.spring.ms_accounts.DTO.response;

import java.math.BigDecimal;

import com.banking.spring.ms_accounts.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDTO {
    private Long accountId;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal initialBalance;
    private BigDecimal availableBalance;
    private Boolean status;
    private Long clientId;
}
