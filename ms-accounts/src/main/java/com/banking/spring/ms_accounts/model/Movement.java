package com.banking.spring.ms_accounts.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.banking.spring.ms_accounts.enums.MovementType;
import com.banking.spring.ms_accounts.utils.SlugGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "movements")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Movement extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;

    @Column(nullable = false, unique = true, updatable = false)
    private String slug;

    @Column(nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @PrePersist
    protected void onCreating() {
        if (this.getSlug() == null || this.getSlug().isBlank()) {
            this.setSlug(SlugGenerator.generate());
        }
    }

    public void anonymize() {
        this.setDeletedAt(LocalDateTime.now());
    }

    public static Movement register(
            BigDecimal amount,
            BigDecimal newBalance,
            MovementType type,
            Account account) {
        return Movement.builder()
                .date(LocalDateTime.now())
                .movementType(type)
                .amount(amount)
                .balance(newBalance)
                .account(account)
                .build();
    }
}