package com.thahirudeen.expense_tracker_api;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record ExpenseResponse(
        Long id,
        BigDecimal amount,
        String description,
        Category category,
        LocalDate dateSpent,
        Instant createdAt
) {

    public static ExpenseResponse from(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getDateSpent(),
                expense.getCreatedAt()
        );
    }
}