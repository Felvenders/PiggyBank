package com.piggyBank.repos;

import com.piggyBank.entity.IncomeCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeCategoriesRepository extends JpaRepository<IncomeCategories, Integer> {
}
