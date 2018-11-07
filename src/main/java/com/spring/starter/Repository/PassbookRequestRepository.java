package com.spring.starter.Repository;

import com.spring.starter.model.PassbookRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PassbookRequestRepository extends JpaRepository<PassbookRequest,Integer> {

    @Query("SELECT adcr FROM PassbookRequest adcr WHERE adcr.customerServiceRequest.customerServiceRequestId=?1")
    Optional<PassbookRequest> getFormFromCSR(int customerServiceRequestId);
}
