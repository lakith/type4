package com.spring.starter.controller;

import com.spring.starter.service.TellerQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Teller-Queue")
public class TelllerQueueController {

    @Autowired
    TellerQueueService tellerQueueService;

    @GetMapping("/genarate-token")
    public ResponseEntity<?> genarateNewGenaralToken(@RequestParam(name="transactionRequestCustomerId")int transactionRequestCustomerId){
        return tellerQueueService.genarateNormalQueuNumber(transactionRequestCustomerId);
    }

    @GetMapping("/get-queue-list")
    public ResponseEntity<?> getQueuelist(){
        return tellerQueueService.getCSRQueueList();
    }

    @GetMapping("/get-hold-queue-list")
    public ResponseEntity<?> getHoldQueueList(){
        return tellerQueueService.getHoldQueueList();
    }

    @GetMapping("/get-pending-queue-list")
    public ResponseEntity<?> getPendingQueueList(){
        return tellerQueueService.getPendingQueue();
    }

    @GetMapping("/hold-a-queue-number")
    public ResponseEntity<?> holdAQueueNumber(@RequestParam(name="customerTransactionId") int customerTransactionId){
        return tellerQueueService.holdAQueueNumber(customerTransactionId);
    }

    @GetMapping("/complete-a-queue-number")
    public ResponseEntity<?> completeAQueueNumber(@RequestParam(name="customerTransactionId") int customerTransactionId) {
        return tellerQueueService.completeAQeueueNumber(customerTransactionId);
    }

    @GetMapping("/get-completed-queue-list")
    public ResponseEntity<?> getCompltetedQueueList() {
        return tellerQueueService.completedQueueList();
    }

    @GetMapping("/continue-a-hold-token")
    public ResponseEntity<?> continueToken(@RequestParam(name="customerTransactionId") int customerTransactionId){
        return tellerQueueService.continueAHoldQueueNumber(customerTransactionId);
    }

    @GetMapping("/genarate-an-special-queue-number")
    public ResponseEntity<?> genarateSpecialToken(@RequestParam(name="transactionRequestCustomerId")int transactionRequestCustomerId){
        return tellerQueueService.issueAPriorityToken(transactionRequestCustomerId);
    }

    @GetMapping("/genarate-redundent-token")
    public ResponseEntity<?> genarateRedundentToken(@RequestParam(name="transactionRequestCustomerId")int transactionRequestCustomerId){
        return tellerQueueService.issueARedundentToken(transactionRequestCustomerId);
    }
}
