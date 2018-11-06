package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authorizer_data_transaction")
public class AuthorizerDataTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorizerDataTransactio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staffUserId")
    private StaffUser staffUser;

    @NotNull
    private String status;

    @NotNull
    private String comment;

    private boolean authoritiesComplete;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerTransactionRequestId")
    @JsonIgnore
    private CustomerTransactionRequest customerTransactionRequest;

    public AuthorizerDataTransaction() {
    }

    public AuthorizerDataTransaction(StaffUser staffUser, String status, String comment) {
        this.staffUser = staffUser;
        this.status = status;
        this.comment = comment;
    }

    public int getAuthorizerDataTransactio() {
        return authorizerDataTransactio;
    }

    public void setAuthorizerDataTransactio(int authorizerDataTransactio) {
        this.authorizerDataTransactio = authorizerDataTransactio;
    }

    public StaffUser getStaffUser() {
        return staffUser;
    }

    public void setStaffUser(StaffUser staffUser) {
        this.staffUser = staffUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isAuthoritiesComplete() {
        return authoritiesComplete;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public void setAuthoritiesComplete(boolean authoritiesComplete) {
        this.authoritiesComplete = authoritiesComplete;
    }
}
