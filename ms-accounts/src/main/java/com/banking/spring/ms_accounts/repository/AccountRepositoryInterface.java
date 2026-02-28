package com.banking.spring.ms_accounts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banking.spring.ms_accounts.model.Account;

public interface AccountRepositoryInterface extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.deletedAt IS NULL")
    List<Account> findAllActive();

    @Query("SELECT a FROM Account a WHERE a.slug = :slug AND a.deletedAt IS NULL")
    Optional<Account> findActiveBySlug(@Param("slug") String slug);

    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.movements m WHERE a.slug = :slug AND a.deletedAt IS NULL")
    Optional<Account> findActiveBySlugWithMovements(@Param("slug") String slug);

    @Query("SELECT a FROM Account a WHERE a.accountNumber = :number AND a.deletedAt IS NULL")
    Optional<Account> findActiveByAccountNumber(@Param("number") String accountNumber);

    boolean existsByAccountNumberAndDeletedAtIsNull(String accountNumber);

    boolean existsByAccountNumberAndDeletedAtIsNullAndSlugNot(String accountNumber, String slug);

}
