package com.spring.starter.Repository;

import com.spring.starter.model.CashWithdrawalUpdateRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CashWithdrawalUpdateRecordsRepository extends JpaRepository<CashWithdrawalUpdateRecords,Integer> {

    @Query("SELECT cwrp FROM CashWithdrawalUpdateRecords cwrp WHERE cwrp.customerTransactionRequest.customerTransactionRequestId=?1")
    Optional<CashWithdrawalUpdateRecords> getFormFromCSR(int customerServiceRequestId);

    @Query("SELECT cwrp FROM CashWithdrawalUpdateRecords cwrp WHERE cwrp.customerTransactionRequest.customerTransactionRequestId=?1")
    List<CashWithdrawalUpdateRecords> getAllFormFromCSR(int customerServiceRequestId);

}
