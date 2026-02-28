package com.banking.spring.ms_accounts.DTO.request;

import java.math.BigDecimal;

import com.banking.spring.ms_accounts.enums.AccountType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountRequestDTO {
    @NotBlank(message = "El número de cuenta es requerido")
    private String accountNumber;

    @NotNull(message = "El tipo de cuenta es requerido")
    private AccountType accountType;

    @NotNull(message = "El saldo inicial es requerido")
    @DecimalMin(value = "0.0", message = "El saldo inicial no puede ser negativo")
    private BigDecimal initialBalance;

    @NotNull(message = "El estado es requerido")
    private Boolean status;

    @NotNull(message = "El cliente es requerido")
    private Long clientId;
}
