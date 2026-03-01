package com.banking.spring.ms_accounts.model;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.banking.spring.ms_accounts.enums.AccountType;
import com.banking.spring.ms_accounts.enums.MovementType;
import com.banking.spring.ms_accounts.exception.InsufficientBalanceException;

@DisplayName("Account model tests")
public class AccountModelTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = Account.builder()
                .accountId(1L)
                .slug("slug-cuenta-123")
                .accountNumber("478758")
                .accountType(AccountType.AHORRO)
                .initialBalance(new BigDecimal("2000.00"))
                .availableBalance(new BigDecimal("2000.00"))
                .status(true)
                .clientId(1L)
                .build();
    }

    @Test
    @DisplayName("Should create a valid Account instance using the builder")
    void applyMovement_shouldCreateDeposito_whenAmountIsPositive() {
        Movement movement = account.applyMovement(new BigDecimal("600.00"));

        assertThat(movement.getMovementType()).isEqualTo(MovementType.DEPOSITO);
        assertThat(movement.getAmount()).isEqualByComparingTo(new BigDecimal("600.00"));
    }

    @Test
    @DisplayName("Should update available balance correctly after a deposit")
    void applyMovement_shouldUpdateAvailableBalance_afterDeposit() {
        account.applyMovement(new BigDecimal("600.00"));

        assertThat(account.getAvailableBalance()).isEqualByComparingTo(new BigDecimal("2600.00"));
    }

    @Test
    @DisplayName("Should create a withdrawal movement when amount is negative")
    void applyMovement_shouldCreateRetiro_whenAmountIsNegative() {
        Movement movement = account.applyMovement(new BigDecimal("-575.00"));

        assertThat(movement.getMovementType()).isEqualTo(MovementType.RETIRO);
        assertThat(movement.getAmount()).isEqualByComparingTo(new BigDecimal("-575.00"));
    }

    @Test
    @DisplayName("Should update available balance correctly after a withdrawal")
    void applyMovement_shouldUpdateAvailableBalance_afterWithdrawal() {
        account.applyMovement(new BigDecimal("-575.00"));

        assertThat(account.getAvailableBalance()).isEqualByComparingTo(new BigDecimal("1425.00"));
    }

    @Test
    @DisplayName("Should return movement with correct balance after applying a withdrawal")
    void applyMovement_shouldReturnMovementWithCorrectBalance() {
        Movement movement = account.applyMovement(new BigDecimal("-575.00"));

        assertThat(movement.getBalance()).isEqualByComparingTo(new BigDecimal("1425.00"));
    }

    @Test
    @DisplayName("Should throw InsufficientBalanceException when trying to withdraw more than available balance")
    void applyMovement_shouldThrowInsufficientBalance_whenNotEnoughFunds() {
        assertThatThrownBy(() -> account.applyMovement(new BigDecimal("-2500.00")))
                .isInstanceOf(InsufficientBalanceException.class)
                .hasMessage("Saldo no disponible");
    }

    @Test
    @DisplayName("Should throw InsufficientBalanceException when trying to withdraw with zero balance")
    void applyMovement_shouldThrowException_whenBalanceIsZeroAndWithdrawing() {
        account = Account.builder()
                .accountNumber("495878")
                .accountType(AccountType.AHORRO)
                .initialBalance(BigDecimal.ZERO)
                .availableBalance(BigDecimal.ZERO)
                .status(true)
                .clientId(3L)
                .build();

        assertThatThrownBy(() -> account.applyMovement(new BigDecimal("-100.00")))
                .isInstanceOf(InsufficientBalanceException.class)
                .hasMessage("Saldo no disponible");
    }

    @Test
    @DisplayName("Should allow withdrawing the exact available balance without throwing an exception")
    void applyMovement_shouldAllowWithdrawingExactBalance() {
        Movement movement = account.applyMovement(new BigDecimal("-2000.00"));

        assertThat(account.getAvailableBalance()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(movement.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should link the created movement to the account")
    void applyMovement_shouldLinkMovementToAccount() {
        Movement movement = account.applyMovement(new BigDecimal("100.00"));

        assertThat(movement.getAccount()).isSameAs(account);
    }

}
