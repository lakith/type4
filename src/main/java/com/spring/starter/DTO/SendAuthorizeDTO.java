package com.spring.starter.DTO;

import com.spring.starter.model.CustomerTransactionRequest;
import com.spring.starter.model.TellerQueue;

public class SendAuthorizeDTO {

    CustomerTransactionRequest customerTransactionRequest;
    TellerQueue tellerQueue;

    public SendAuthorizeDTO() {
    }

    public SendAuthorizeDTO(CustomerTransactionRequest customerTransactionRequest, TellerQueue tellerQueue) {
        this.customerTransactionRequest = customerTransactionRequest;
        this.tellerQueue = tellerQueue;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public TellerQueue getTellerQueue() {
        return tellerQueue;
    }

    public void setTellerQueue(TellerQueue tellerQueue) {
        this.tellerQueue = tellerQueue;
    }
}
