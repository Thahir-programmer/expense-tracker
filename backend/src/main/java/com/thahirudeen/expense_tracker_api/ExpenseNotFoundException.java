package com.thahirudeen.expense_tracker_api;

public class ExpenseNotFoundException extends RuntimeException {

    public ExpenseNotFoundException(Long id) {
        super("Expense with id " + id + " not found");
    }
}