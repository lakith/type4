package com.spring.starter.Repository;

import com.spring.starter.model.FundTransferSLIPSBreakDown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FundTransferSLIPSBreakDownRepository extends JpaRepository<FundTransferSLIPSBreakDown,Integer> {

    @Query("SELECT b FROM FundTransferSLIPSBreakDown b WHERE b.fundTransferSLIPS.fundTransferSLIPSId =:fundTransferSLIPSId")
    public Optional<FundTransferSLIPSBreakDown> findBreakDown(@Param("fundTransferSLIPSId") int fundTransferSLIPSId);

}
