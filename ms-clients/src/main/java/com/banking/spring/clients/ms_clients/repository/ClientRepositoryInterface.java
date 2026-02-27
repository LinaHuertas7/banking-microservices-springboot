package com.banking.spring.clients.ms_clients.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.spring.clients.ms_clients.model.Client;

public interface ClientRepositoryInterface extends JpaRepository<Client, Long> {
    Optional<Client> findByIdentification(String identification);

    boolean existsByIdentification(String identification);
}
