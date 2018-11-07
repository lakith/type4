package com.spring.starter.Repository;

import com.spring.starter.model.FundTransferWithinNDBUpdateRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FundTransferWithinNDBUpdateRecordRepository extends JpaRepository<FundTransferWithinNDBUpdateRecord,Integer> {

    @Query("SELECT ftwnur FROM FundTransferWithinNDBUpdateRecord ftwnur WHERE ftwnur.customerTransactionRequest.customerTransactionRequestId=?1")
    Optional<FundTransferWithinNDBUpdateRecord> getFormFromCSR(int customerServiceRequestId);

    @Query("SELECT ftwnur FROM FundTransferWithinNDBUpdateRecord ftwnur WHERE ftwnur.customerTransactionRequest.customerTransactionRequestId=?1")
    List<FundTransferWithinNDBUpdateRecord> getAllFormFromCSR(int customerServiceRequestId);
}
