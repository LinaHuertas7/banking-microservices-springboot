package com.banking.spring.clients.ms_clients.model;

import java.time.LocalDateTime;

import com.banking.spring.clients.ms_clients.utils.SlugGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Client extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status;

    @PrePersist
    protected void onCreating() {
        if (this.slug == null || this.slug.isBlank()) {
            this.slug = SlugGenerator.generate();
        }
    }

    public void anonymize() {
        this.setDeletedAt(LocalDateTime.now());
        this.setStatus(false);
        this.setIdentification(this.getIdentification() + "_DELETED");
        this.setName("");
        this.setPhone("");
        this.setAddress("");
        this.setPassword("");
    }
}