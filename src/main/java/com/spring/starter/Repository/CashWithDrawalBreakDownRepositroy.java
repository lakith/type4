package com.spring.starter.Repository;

import com.spring.starter.model.CashWithDrawalBreakDown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CashWithDrawalBreakDownRepositroy extends JpaRepository<CashWithDrawalBreakDown,Integer> {

    @Query("SELECT b FROM CashWithDrawalBreakDown b WHERE b.cashWithdrawal.cashWithdrawalId =:cashWithdrawalId")
    public Optional<CashWithDrawalBreakDown> findBreakDown(@Param("cashWithdrawalId") int cashWithdrawalId);

}
