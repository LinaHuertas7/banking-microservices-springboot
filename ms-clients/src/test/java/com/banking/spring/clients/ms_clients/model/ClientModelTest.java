package com.banking.spring.clients.ms_clients.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.banking.spring.clients.ms_clients.enums.Gender;

@DisplayName("Client model tests")
class ClientModelTest {

    private Client client;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .name("Jose Lema")
                .gender(Gender.MALE)
                .age(35)
                .identification("1234567890")
                .address("Otavalo sn y principal")
                .phone("098254785")
                .password("encodedPassword")
                .status(true)
                .slug("test-slug-abc")
                .build();
    }

    @Test
    @DisplayName("Should build a client with all attributes correctly assigned")
    void shouldBuildClientWithAllAttributes() {
        assertThat(client.getName()).isEqualTo("Jose Lema");
        assertThat(client.getGender()).isEqualTo(Gender.MALE);
        assertThat(client.getAge()).isEqualTo(35);
        assertThat(client.getIdentification()).isEqualTo("1234567890");
        assertThat(client.getAddress()).isEqualTo("Otavalo sn y principal");
        assertThat(client.getPhone()).isEqualTo("098254785");
        assertThat(client.getPassword()).isEqualTo("encodedPassword");
        assertThat(client.getStatus()).isTrue();
    }

    @Test
    @DisplayName("Should set status to false when anonymized")
    void shouldSetStatusFalseWhenAnonymized() {
        client.anonymize();

        assertThat(client.getStatus()).isFalse();
    }

    @Test
    @DisplayName("Should append '_DELETED' to identification when anonymized")
    void shouldAppendDeletedSuffixToIdentificationWhenAnonymized() {
        String originalId = client.getIdentification();

        client.anonymize();

        assertThat(client.getIdentification()).isEqualTo(originalId + "_DELETED");
    }

    @Test
    @DisplayName("Should clear personal data when anonymized")
    void shouldClearPersonalDataWhenAnonymized() {
        client.anonymize();

        assertThat(client.getName()).isEmpty();
        assertThat(client.getPhone()).isEmpty();
        assertThat(client.getAddress()).isEmpty();
        assertThat(client.getPassword()).isEmpty();
    }

    @Test
    @DisplayName("Should set deletedAt timestamp when anonymized")
    void shouldSetDeletedAtWhenAnonymized() {
        assertThat(client.getDeletedAt()).isNull();

        client.anonymize();

        assertThat(client.getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("Should have status true for an active client")
    void activeClientShouldHaveStatusTrue() {
        assertThat(client.getStatus()).isTrue();
    }

    @Test
    @DisplayName("Should have null deletedAt for an active client")
    void activeClientShouldHaveNullDeletedAt() {
        assertThat(client.getDeletedAt()).isNull();
    }
}