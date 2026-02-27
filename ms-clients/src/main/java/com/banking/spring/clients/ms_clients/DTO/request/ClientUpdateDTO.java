package com.banking.spring.clients.ms_clients.DTO.request;

import com.banking.spring.clients.ms_clients.enums.Gender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientUpdateDTO {
    private String name;
    private Gender gender;
    private Integer age;
    private String address;
    private String phone;
    private String password;
    private Boolean status;
}
