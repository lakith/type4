package com.spring.starter.Repository;

import com.spring.starter.model.CSRDataTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CSRDataTransactionRepository extends JpaRepository<CSRDataTransaction,Integer> {
}
