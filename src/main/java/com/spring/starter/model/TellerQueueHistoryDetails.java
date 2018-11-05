package com.spring.starter.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "teller_queue_history_details")
public class TellerQueueHistoryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int telerQueueId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="transactionCustomerId")
    private TransactionCustomer transactionCustomer;

    @Column
    private String queueNumber;

    @Column
    private boolean complete;

    @Column
    private String comment;

    @Column
    private boolean hold;

    @Column
    private boolean queueReject;

    @Column
    private boolean queuePending;

    @Column
    private Date queueStartDate;

    @Column
    private Date queueEndDate;

    private double queueNumIdentification;

    public TellerQueueHistoryDetails() {
    }

    public TellerQueueHistoryDetails(TransactionCustomer transactionCustomer, String queueNumber, boolean complete,
                                     String comment, boolean hold, boolean queueReject, boolean queuePending,
                                     Date queueStartDate, Date queueEndDate, double queueNumIdentification) {
        this.transactionCustomer = transactionCustomer;
        this.queueNumber = queueNumber;
        this.complete = complete;
        this.comment = comment;
        this.hold = hold;
        this.queueReject = queueReject;
        this.queuePending = queuePending;
        this.queueStartDate = queueStartDate;
        this.queueEndDate = queueEndDate;
        this.queueNumIdentification = queueNumIdentification;
    }

    public int getTelerQueueId() {
        return telerQueueId;
    }

    public void setTelerQueueId(int telerQueueId) {
        this.telerQueueId = telerQueueId;
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
