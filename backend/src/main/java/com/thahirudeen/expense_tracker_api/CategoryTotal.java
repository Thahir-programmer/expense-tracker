package com.thahirudeen.expense_tracker_api;

import java.math.BigDecimal;

public record CategoryTotal(
        Category category,
        BigDecimal total
) {
}