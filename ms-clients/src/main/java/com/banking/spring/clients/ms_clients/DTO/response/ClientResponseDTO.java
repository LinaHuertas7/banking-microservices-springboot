package com.banking.spring.clients.ms_clients.DTO.response;

import com.banking.spring.clients.ms_clients.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
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
