package com.piggyBank.repos;

import com.piggyBank.entity.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationType, Integer> {
}
