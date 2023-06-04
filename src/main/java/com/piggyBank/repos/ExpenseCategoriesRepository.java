package com.piggyBank.repos;

import com.piggyBank.entity.ExpenseCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseCategoriesRepository extends JpaRepository<ExpenseCategories, Integer> {
}
