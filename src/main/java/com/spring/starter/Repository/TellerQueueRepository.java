package com.spring.starter.Repository;

import com.spring.starter.model.CSRQueue;
import com.spring.starter.model.TellerQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TellerQueueRepository extends JpaRepository<TellerQueue,Integer> {

    @Query("SELECT Count(c) FROM TellerQueue c")
    public int getCurrentListCount();

    @Query("SELECT c FROM TellerQueue c WHERE c.transactionCustomer.transactionCustomerId = ?1")
    public Optional<TellerQueue> checkTokenStatus(int customerID);

    @Query("SELECT c FROM TellerQueue c ORDER BY c.queueNumIdentification")
    public List<TellerQueue> getAllQueueNumbers();

    @Query("SELECT c FROM TellerQueue c WHERE c.queueNumber=?1")
    public Optional<TellerQueue> getTellerQueueById(String queueNumber);

    @Query("SELECT c FROM TellerQueue c WHERE c.hold = true and c.complete=false ORDER BY c.queueNumIdentification")
    public List<TellerQueue> getholdQueue();

    @Query("SELECT c FROM TellerQueue c WHERE c.hold = false and c.complete=false and c.queuePending =true ORDER BY c.queueNumIdentification")
    public List<TellerQueue> getpendingQueue();

    @Query("SELECT c FROM TellerQueue c WHERE c.hold = false and c.complete=true and c.queuePending =false ORDER BY c.queueNumIdentification")
    public List<TellerQueue> getCompletedQueue();

    @Query("SELECT c FROM TellerQueue c WHERE c.transactionCustomer.transactionCustomerId=?1")
    public Optional<TellerQueue> getTellerQueueByCustomerId(int customerID);


}
