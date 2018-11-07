package com.spring.starter.Repository;

import com.spring.starter.model.BillPaymentCashBreakDown;
import com.spring.starter.model.CashDepositBreakDown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BillPaymentCashBreakDownRepository extends JpaRepository<BillPaymentCashBreakDown,Integer> {

    @Query("SELECT b FROM BillPaymentCashBreakDown b WHERE b.billPayment.billPaymentId =?1")
    public Optional<CashDepositBreakDown> findBreakDown(int cashDepositId);

}
