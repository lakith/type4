package com.spring.starter.service;

import org.springframework.http.ResponseEntity;

public interface TellerQueueService {

    public ResponseEntity<?> completeAQeueueNumber(int customerId);

    public ResponseEntity<?> issueARedundentToken(int customerTransactionRequestId);

    public ResponseEntity<?> issueAPriorityToken(int customerServiceRequestId);

    public ResponseEntity<?> continueAHoldQueueNumber(int customerId);

    public ResponseEntity<?> completedQueueList();

    public ResponseEntity<?> getPendingQueue();

    public ResponseEntity<?> getHoldQueueList();

    public ResponseEntity<?> holdAQueueNumber(int customerId);

    public ResponseEntity<?> getCSRQueueList();

    public ResponseEntity<?> genarateNormalQueuNumber(int customerTransactionRequestId);

}
