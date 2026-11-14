package com.thahirudeen.expense_tracker_api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , precision = 12 , scale = 2)
    private BigDecimal amount;
    @Column(length = 255)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Category category;
    @Column(name = "date_spent" , nullable = false)
    private LocalDate dateSpent;
    @Column(name = "created_at" , nullable = false , insertable = false)
    private Instant createdAt;


    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public BigDecimal getAmount() {
    return amount;
}

public void setAmount(BigDecimal amount) {
    this.amount = amount;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

public Category getCategory() {
    return category;
}

public void setCategory(Category category) {
    this.category = category;
}

public LocalDate getDateSpent() {
    return dateSpent;
}

public void setDateSpent(LocalDate dateSpent) {
    this.dateSpent = dateSpent;
}

public Instant getCreatedAt() {
    return createdAt;
}
}