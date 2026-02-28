package com.banking.spring.ms_accounts.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banking.spring.ms_accounts.model.Movement;

public interface MovementRepositoryInterface extends JpaRepository<Movement, Long> {

        @Query("SELECT m FROM Movement m WHERE m.deletedAt IS NULL AND m.account.deletedAt IS NULL ORDER BY m.date DESC")
        List<Movement> findAllActive();

        @Query("SELECT m FROM Movement m  WHERE m.slug=:slug AND m.deletedAt IS NULL AND m.account.deletedAt IS NULL")
        Optional<Movement> findActiveBySlug(@Param("slug") String slug);

        @Query(" SELECT m FROM Movement m JOIN FETCH m.account a WHERE a.clientId = :clientId AND m.date BETWEEN :start AND :end AND m.deletedAt IS NULL AND a.deletedAt IS NULL ORDER BY m.date DESC")
        List<Movement> findByClientIdAndDateRange(
                        @Param("clientId") Long clientId,
                        @Param("start") LocalDateTime from,
                        @Param("end") LocalDateTime to);

        @Modifying
        @Query("UPDATE Movement m SET m.deletedAt = :deletedAt WHERE m.account.accountId = :accountId AND m.deletedAt IS NULL")
        void softDeleteByAccountId(
                        @Param("accountId") Long accountId,
                        @Param("deletedAt") LocalDateTime deletedAt);
}
