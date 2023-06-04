package com.piggyBank.repos;

import com.piggyBank.entity.Operations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationsRepository extends JpaRepository<Operations, Integer> {

    @Query("select oper from Operations oper where extract(year from oper.date) = extract(year from current_date) and extract(month from oper.date) = extract(month from current_date) and oper.users.id = :id")
    List<Operations> findInCurrentMonth(Integer id);
}
