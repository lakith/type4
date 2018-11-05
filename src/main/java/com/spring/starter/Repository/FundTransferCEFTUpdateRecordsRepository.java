package com.spring.starter.Repository;

import com.spring.starter.model.FundTransferCEFTUpdateRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FundTransferCEFTUpdateRecordsRepository extends JpaRepository<FundTransferCEFTUpdateRecords,Integer> {

    @Query("SELECT a FROM FundTransferCEFTUpdateRecords a WHERE a.customerTransactionRequest.customerTransactionRequestId=?1")
    Optional<FundTransferCEFTUpdateRecords> getFormFromCSR(int customerServiceRequestId);

    @Query("SELECT a FROM FundTransferCEFTUpdateRecords a WHERE a.customerTransactionRequest.customerTransactionRequestId=?1")
    List<FundTransferCEFTUpdateRecords> getAllFormFromCSR(int customerServiceRequestId);


}
