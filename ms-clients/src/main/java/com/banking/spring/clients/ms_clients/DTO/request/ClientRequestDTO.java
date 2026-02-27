package com.banking.spring.clients.ms_clients.DTO.request;

import com.banking.spring.clients.ms_clients.enums.Gender;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ClientRequestDTO {
    @NotBlank(message = "Nombre es requerido")
    private String name;

    @NotNull(message = "Género es requerido")
    private Gender gender;

    @NotNull(message = "Edad es requerido")
    @Min(value = 0, message = "La edad debe ser mayor a cero")
    private Integer age;

    @NotBlank(message = "La identificación es requerida")
    private String identification;

    private String address;

    private String phone;

    @NotBlank(message = "La contraseña es requerida")
    private String password;

    @NotNull(message = "El estado es requerido")
    private Boolean status;
}
