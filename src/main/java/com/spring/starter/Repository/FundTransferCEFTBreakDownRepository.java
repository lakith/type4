package com.spring.starter.Repository;

import com.spring.starter.model.FundTransferCEFTBreakDown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FundTransferCEFTBreakDownRepository extends JpaRepository<FundTransferCEFTBreakDown,Integer> {

    @Query("SELECT b FROM FundTransferCEFTBreakDown b WHERE b.fundTransferCEFT.fundTransferCEFTId =:fundTransferCEFTId")
    public Optional<FundTransferCEFTBreakDown> findBreakDown(@Param("fundTransferCEFTId") int fundTransferCEFTId);

}
