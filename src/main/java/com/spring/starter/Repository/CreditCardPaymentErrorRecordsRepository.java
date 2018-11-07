package com.spring.starter.Repository;

import com.spring.starter.model.CreditCardPaymentErrorRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardPaymentErrorRecordsRepository extends JpaRepository<CreditCardPaymentErrorRecords,Integer> {
}
