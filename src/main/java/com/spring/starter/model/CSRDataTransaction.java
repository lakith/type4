package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "csr_data_transaction")
public class CSRDataTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int csrDataTransactionId;

    @OneToOne
    @JoinColumn(name = "staffUserId")
    private StaffUser staffUser;

    private String status;

    private String comment;

    private Date date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerTransactionRequestId")
    @JsonIgnore
    private CustomerTransactionRequest customerTransactionRequest;

    public CSRDataTransaction() {
    }

    public CSRDataTransaction(StaffUser staffUser, String status, String comment) {
        this.staffUser = staffUser;
        this.status = status;
        this.comment = comment;
    }

    public int getCsrDataTransactionId() {
        return csrDataTransactionId;
    }

    public void setCsrDataTransactionId(int csrDataTransactionId) {
        this.csrDataTransactionId = csrDataTransactionId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }
}