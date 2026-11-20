package com.spendwise.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spendwise.entity.User;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    // =====================================
    // TRANSACTION ID
    // =====================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =====================================
    // TRANSACTION DETAILS
    // =====================================

    private String description;

    private String category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    private String type;

    private LocalDate date;

    // =====================================
    // LINK TRANSACTION WITH USER
    // =====================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    @JsonIgnore
    private User user;

    // =====================================
    // DEFAULT CONSTRUCTOR
    // =====================================

    public Transaction() {
    }

    // =====================================
    // GETTERS AND SETTERS
    // =====================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}