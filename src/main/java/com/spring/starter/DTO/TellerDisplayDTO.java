package com.spring.starter.DTO;

import com.spring.starter.model.CSRQueue;
import com.spring.starter.model.Customer;
import com.spring.starter.model.TransactionCustomer;

import java.util.Date;

public class TellerDisplayDTO {

    private boolean success ;
    private CSRQueue csrQueue;
    private String message;
    private String tel;

    private int tellerQueueId;

    private TransactionCustomer transactionCustomer;


    private String queueNumber;


    private boolean complete;


    private String comment;

    private boolean hold;


    private boolean queueReject;


    private boolean queuePending;


    private Date queueStartDate;


    private Date queueEndDate;

    private double queueNumIdentification;


    public TellerDisplayDTO() {
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CSRQueue getCsrQueue() {
        return csrQueue;
    }

    public void setCsrQueue(CSRQueue csrQueue) {
        this.csrQueue = csrQueue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getTellerQueueId() {
        return tellerQueueId;
    }

    public void setTellerQueueId(int tellerQueueId) {
        this.tellerQueueId = tellerQueueId;
    }

    public TransactionCustomer getTransactionCustomer() {
        return transactionCustomer;
    }

    public void setTransactionCustomer(TransactionCustomer transactionCustomer) {
        this.transactionCustomer = transactionCustomer;
    }

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isHold() {
        return hold;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    public boolean isQueueReject() {
        return queueReject;
    }

    public void setQueueReject(boolean queueReject) {
        this.queueReject = queueReject;
    }

    public boolean isQueuePending() {
        return queuePending;
    }

    public void setQueuePending(boolean queuePending) {
        this.queuePending = queuePending;
    }

    public Date getQueueStartDate() {
        return queueStartDate;
    }

    public void setQueueStartDate(Date queueStartDate) {
        this.queueStartDate = queueStartDate;
    }

    public Date getQueueEndDate() {
        return queueEndDate;
    }

    public void setQueueEndDate(Date queueEndDate) {
        this.queueEndDate = queueEndDate;
    }

    public double getQueueNumIdentification() {
        return queueNumIdentification;
    }

    public void setQueueNumIdentification(double queueNumIdentification) {
        this.queueNumIdentification = queueNumIdentification;
    }
}
