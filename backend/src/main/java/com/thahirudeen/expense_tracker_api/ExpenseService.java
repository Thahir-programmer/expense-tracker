package com.thahirudeen.expense_tracker_api;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import org.springframework.data.domain.Sort;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Transactional
public ExpenseResponse create(ExpenseRequest request) {

    Expense expense = new Expense();

    expense.setAmount(request.amount());
    expense.setDescription(request.description());
    expense.setCategory(request.category());
    expense.setDateSpent(request.dateSpent());

    Expense savedExpense = expenseRepository.save(expense);

    return ExpenseResponse.from(savedExpense);
}

@Transactional(readOnly = true)
public List<ExpenseResponse> list() {
    return expenseRepository.findAll(
            Sort.by(Sort.Direction.DESC, "dateSpent")
    ).stream()
            .map(ExpenseResponse::from)
            .toList();
}

@Transactional
public ExpenseResponse update(Long id, ExpenseRequest request) {

    Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseNotFoundException(id));

    expense.setAmount(request.amount());
    expense.setDescription(request.description());
    expense.setCategory(request.category());
    expense.setDateSpent(request.dateSpent());

    Expense updatedExpense = expenseRepository.save(expense);

    return ExpenseResponse.from(updatedExpense);
}

@Transactional
public void delete(Long id) {

    Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseNotFoundException(id));

    expenseRepository.delete(expense);
}
}