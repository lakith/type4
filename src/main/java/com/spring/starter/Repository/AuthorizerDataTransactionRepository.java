package com.spring.starter.Repository;

import com.spring.starter.model.AuthorizerDataTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizerDataTransactionRepository extends JpaRepository<AuthorizerDataTransaction,Integer> {
}
