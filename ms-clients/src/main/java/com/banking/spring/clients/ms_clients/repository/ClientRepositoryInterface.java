package com.banking.spring.clients.ms_clients.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banking.spring.clients.ms_clients.model.Client;

public interface ClientRepositoryInterface extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.deletedAt IS NULL")
    List<Client> findAllActive();

    @Query("SELECT c FROM Client c WHERE c.clientId = :id AND c.deletedAt IS NULL")
    Optional<Client> findActiveById(@Param("id") Long id);

    boolean existsByIdentification(String identification);

    @Modifying
    @Query("UPDATE Client c SET c.deletedAt = NULL, c.status = true WHERE c.clientId = :id")
    void restore(@Param("id") Long id);
}
