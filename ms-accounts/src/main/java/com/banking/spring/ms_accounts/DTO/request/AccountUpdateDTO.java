package com.banking.spring.ms_accounts.DTO.request;

import java.math.BigDecimal;

import com.banking.spring.ms_accounts.enums.AccountType;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class AccountUpdateDTO {
    private AccountType accountType;

    @DecimalMin(value = "0.0", message = "El saldo no puede ser negativo")
    private BigDecimal initialBalance;

    private Boolean status;
}
