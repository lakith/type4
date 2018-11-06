package com.spring.starter.DTO;

import com.spring.starter.model.CSRQueue;
import com.spring.starter.model.Customer;

import javax.persistence.*;
import java.util.Date;

public class CSRDisplayDTO {

    private boolean success ;
    private CSRQueue csrQueue;
    private String message;
    private String tel;

    private int CSRQueueId;

    private Customer customer;


    private String queueNumber;


    private boolean complete;


    private String comment;

    private boolean hold;


    private boolean queueReject;


    private boolean queuePending;


    private Date queueStartDate;


    private Date queueEndDate;

    private double queueNumIdentification;

    public CSRDisplayDTO() {
    }

    public CSRDisplayDTO(boolean success, CSRQueue csrQueue, String tel) {
        this.success = success;
        this.csrQueue = csrQueue;
        this.tel = tel;
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

    public String getTel() {
        return tel;
    }

    public int getCSRQueueId() {
        return CSRQueueId;
    }

    public void setCSRQueueId(int CSRQueueId) {
        this.CSRQueueId = CSRQueueId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
