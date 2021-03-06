package com.spring.starter.Repository;

import com.spring.starter.model.BillPayment;
import com.spring.starter.model.CashDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CashdepositRepositiry extends JpaRepository<CashDeposit,Integer> {

    @Query("SELECT cd FROM CashDeposit cd WHERE cd.customerTransactionRequest.customerTransactionRequestId=?1")
    Optional<CashDeposit> getFormFromCSR(int customerTransactionRequestID);

}
