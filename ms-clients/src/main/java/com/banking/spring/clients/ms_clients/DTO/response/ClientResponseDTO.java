package com.banking.spring.clients.ms_clients.DTO.response;

import com.banking.spring.clients.ms_clients.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {
    private Long clientId;
    private String name;
    private Gender gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;
    private Boolean status;
}
