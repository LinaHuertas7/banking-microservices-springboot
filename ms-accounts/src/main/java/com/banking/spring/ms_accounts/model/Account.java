package com.banking.spring.ms_accounts.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.banking.spring.ms_accounts.enums.AccountType;
import com.banking.spring.ms_accounts.enums.MovementType;
import com.banking.spring.ms_accounts.exception.InsufficientBalanceException;
import com.banking.spring.ms_accounts.utils.SlugGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class Account extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, unique = true, updatable = false)
    private String slug;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal initialBalance;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal availableBalance;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private Long clientId;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Movement> movements;

    @PrePersist
    protected void onCreating() {
        if (this.getSlug() == null || this.getSlug().isBlank()) {
            this.setSlug(SlugGenerator.generate());
        }
    }

    public Movement applyMovement(BigDecimal amount) {
        BigDecimal newBalance = this.availableBalance.add(amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("Saldo no disponible");
        }

        MovementType type = amount.compareTo(BigDecimal.ZERO) >= 0
                ? MovementType.DEPOSITO
                : MovementType.RETIRO;

        this.availableBalance = newBalance;

        return Movement.register(amount, newBalance, type, this);
    }

    public void anonymize() {
        this.setDeletedAt(LocalDateTime.now());
        this.setStatus(false);
        this.setAccountNumber(this.getAccountNumber() + "_DELETED");
    }
}
