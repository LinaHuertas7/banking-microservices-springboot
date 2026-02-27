package com.banking.spring.clients.ms_clients.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status;
}
