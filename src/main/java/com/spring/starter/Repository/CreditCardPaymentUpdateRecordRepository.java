package com.spring.starter.Repository;

import com.spring.starter.model.CreditCardPaymentUpdateRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditCardPaymentUpdateRecordRepository extends JpaRepository<CreditCardPaymentUpdateRecord,Integer> {

    @Query("SELECT a FROM CreditCardPaymentUpdateRecord a WHERE a.customerTransactionRequest.customerTransactionRequestId=?1")
    List<CreditCardPaymentUpdateRecord> getAllFormFromCSR(int customerServiceRequestId);


}
