package com.spring.starter.Repository;

import com.spring.starter.model.CashDepositBreakDown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CashDepositBreakDownRepository extends JpaRepository<CashDepositBreakDown,Integer> {

    @Query("SELECT b FROM CashDepositBreakDown b WHERE b.cashDeposit.cashDepositId =:cashDepositId")
    public Optional<CashDepositBreakDown> findBreakDown(@Param("cashDepositId") int cashDepositId);

}
