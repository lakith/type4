package com.spring.starter.Repository;

import com.spring.starter.model.BillPayment;
import com.spring.starter.model.CrediitCardPeyment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CreditCardPeymentRepository  extends JpaRepository<CrediitCardPeyment,Integer> {

    @Query("SELECT cp FROM CrediitCardPeyment cp WHERE cp.customerTransactionRequest.customerTransactionRequestId=?1")
    Optional<CrediitCardPeyment> getFormFromCSR(int customerTransactionRequestID);
}
