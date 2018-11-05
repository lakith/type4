package com.spring.starter.Repository;

import com.spring.starter.model.FundTransferSLIPSUpdateRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FundTransferSLIPSUpdateRecordRepository extends JpaRepository<FundTransferSLIPSUpdateRecord,Integer> {

    @Query("SELECT a FROM FundTransferSLIPSUpdateRecord a WHERE a.customerTransactionRequest.customerTransactionRequestId=?1")
    Optional<FundTransferSLIPSUpdateRecord> getFormFromCSR(int customerServiceRequestId);

    @Query("SELECT a FROM FundTransferSLIPSUpdateRecord a WHERE a.customerTransactionRequest.customerTransactionRequestId=?1")
    List<FundTransferSLIPSUpdateRecord> getAllFormFromCSR(int customerServiceRequestId);

}
