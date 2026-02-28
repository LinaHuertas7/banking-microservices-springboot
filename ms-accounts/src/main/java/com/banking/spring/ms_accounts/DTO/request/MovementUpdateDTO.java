package com.banking.spring.ms_accounts.DTO.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MovementUpdateDTO {
    private BigDecimal amount;
}
