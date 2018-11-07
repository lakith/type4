package com.spring.starter.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="customer_transaction_request")
public class CustomerTransactionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerTransactionRequestId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="transactionRequestId")
    private TransactionRequest transactionRequest;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="transactionCustomerId")
    private TransactionCustomer transactionCustomer;

    private boolean status = false;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="customerTransactionRequestId")
    private List<StaffUser> staffUser;

    private boolean authorize=false;

    private boolean softReject = false;

    @OneToMany
    @JoinColumn(name = "customerTransactionRequestId")
    private List<CSRDataTransaction> csrDataTransaction;

    @OneToOne
    @JoinColumn(name = "authorizerDataTransactionRequestId")
    private AuthorizerDataTransaction authorizerDataTransaction;

    private String statusMessage;

    private String ttNumber;

    private Date requestDate;

    private Date requestCompleteDate;

    public CustomerTransactionRequest() {
    }

    public CustomerTransactionRequest(TransactionRequest transactionRequest, TransactionCustomer transactionCustomer,
                                      boolean status, List<StaffUser> staffUser, Date requestDate) {
        this.transactionRequest = transactionRequest;
        this.transactionCustomer = transactionCustomer;
        this.status = status;
        this.staffUser = staffUser;
        this.requestDate = requestDate;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getCustomerTransactionRequestId() {
        return customerTransactionRequestId;
    }

    public void setCustomerTransactionRequestId(int customerTransactionRequestId) {
        this.customerTransactionRequestId = customerTransactionRequestId;
    }

    public TransactionRequest getTransactionRequest() {
        return transactionRequest;
    }

    public void setTransactionRequest(TransactionRequest transactionRequest) {
        this.transactionRequest = transactionRequest;
    }

    public TransactionCustomer getTransactionCustomer() {
        return transactionCustomer;
    }

    public void setTransactionCustomer(TransactionCustomer transactionCustomer) {
        this.transactionCustomer = transactionCustomer;
    }

    public List<CSRDataTransaction> getCsrDataTransaction() {
        return csrDataTransaction;
    }

    public void setCsrDataTransaction(List<CSRDataTransaction> csrDataTransaction) {
        this.csrDataTransaction = csrDataTransaction;
    }

    public AuthorizerDataTransaction getAuthorizerDataTransaction() {
        return authorizerDataTransaction;
    }

    public void setAuthorizerDataTransaction(AuthorizerDataTransaction authorizerDataTransaction) {
        this.authorizerDataTransaction = authorizerDataTransaction;
    }

    public boolean isAuthorize() {
        return authorize;
    }

    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }

    public String getTtNumber() {
        return ttNumber;
    }

    public boolean isSoftReject() {
        return softReject;
    }

    public void setSoftReject(boolean softReject) {
        this.softReject = softReject;
    }

    public void setTtNumber(String ttNumber) {
        this.ttNumber = ttNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<StaffUser> getStaffUser() {
        return staffUser;
    }

    public void setStaffUser(List<StaffUser> staffUser) {
        this.staffUser = staffUser;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getRequestCompleteDate() {
        return requestCompleteDate;
    }

    public void setRequestCompleteDate(Date requestCompleteDate) {
        this.requestCompleteDate = requestCompleteDate;
    }
}
