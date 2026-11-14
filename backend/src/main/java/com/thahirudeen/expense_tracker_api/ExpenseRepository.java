package com.thahirudeen.expense_tracker_api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("""
        select new com.thahirudeen.expense_tracker_api.CategoryTotal(
            e.category,
            sum(e.amount)
        )
        from Expense e
        where e.dateSpent >= :start
          and e.dateSpent < :end
        group by e.category
        order by sum(e.amount) desc
        """)
    List<CategoryTotal> findCategoryTotalsBetween(
        @Param("start") LocalDate start,
        @Param("end") LocalDate end
);
}