package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "passBook_request")
public class PassbookRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int passbookRequest;
    @NotNull
    private long accountNumber;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    public PassbookRequest() {
    }

    public PassbookRequest(long accountNumber, CustomerServiceRequest customerServiceRequest) {
        this.accountNumber = accountNumber;
        this.customerServiceRequest = customerServiceRequest;
    }

    public int getPassbookRequest() {
        return passbookRequest;
    }

    public void setPassbookRequest(int passbookRequest) {
        this.passbookRequest = passbookRequest;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
