package com.spring.starter.service;

import org.springframework.http.ResponseEntity;

public interface CSRQueueService {

    public ResponseEntity<?> genarateNormalQueuNumber(int customerServiceRequestId);

    public ResponseEntity<?> getCSRQueueList();

    public ResponseEntity<?> getPendingQueue();

    public ResponseEntity<?> getHoldQueueList();

    public ResponseEntity<?> holdAQueueNumber(int customerId);

    public ResponseEntity<?> completeAQeueueNumber(int customerId);

    public ResponseEntity<?> completedQueueList();

    public ResponseEntity<?> continueAHoldQueueNumber(int customerId);

    public ResponseEntity<?> issueAPriorityToken(int customerServiceRequestId);

    public ResponseEntity<?> issueARedundentToken(int customerServiceRequestId);

}
