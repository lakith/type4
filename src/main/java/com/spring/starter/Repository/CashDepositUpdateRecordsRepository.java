package com.spring.starter.Repository;

import com.spring.starter.model.CashDepositUpdateRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CashDepositUpdateRecordsRepository extends JpaRepository<CashDepositUpdateRecords,Integer> {

    @Query("SELECT a FROM CashDepositUpdateRecords a WHERE a.customerTransactionRequest.customerTransactionRequestId=?1")
    Optional<CashDepositUpdateRecords> getFormFromCSR(int customerServiceRequestId);

    @Query("SELECT a FROM CashDepositUpdateRecords a WHERE a.customerTransactionRequest.customerTransactionRequestId=?1")
    List<CashDepositUpdateRecords> getAllFormFromCSR(int customerServiceRequestId);

}
