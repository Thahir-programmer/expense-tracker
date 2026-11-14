package com.thahirudeen.expense_tracker_api;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ExpenseRequest(

        @NotNull
        @Positive
        @Digits(integer = 10, fraction = 2)
        BigDecimal amount,

        @Size(max = 255)
        String description,

        @NotNull
        Category category,

        @NotNull
        @PastOrPresent
        LocalDate dateSpent

) {
}