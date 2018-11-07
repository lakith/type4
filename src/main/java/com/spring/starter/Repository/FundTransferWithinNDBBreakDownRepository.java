package com.spring.starter.Repository;

import com.spring.starter.model.FundTransferWithinNDBBreakDown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FundTransferWithinNDBBreakDownRepository extends JpaRepository<FundTransferWithinNDBBreakDown,Integer> {

    @Query("SELECT b FROM FundTransferWithinNDBBreakDown b WHERE b.fundTransferWithinNDB.fundTransferWithinNdbId =:fundTransferWithinNdbId")
    public Optional<FundTransferWithinNDBBreakDown> findBreakDown(@Param("fundTransferWithinNdbId") int fundTransferWithinNdbId);

}
