package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "credit_card_payment_Error_records")
public class CreditCardPaymentErrorRecords {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int creditCardPaymentErrorRecordsId;

    private String oldValue;

    private String newValue;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "creditCardPaymentUpdateRecordId")
    @JsonIgnore
    private CreditCardPaymentUpdateRecord CreditCardPaymentUpdateRecord;

    public CreditCardPaymentErrorRecords() {
    }

    public CreditCardPaymentErrorRecords(String oldValue, String newValue, String comment, com.spring.starter.model.CreditCardPaymentUpdateRecord creditCardPaymentUpdateRecord) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.comment = comment;
        CreditCardPaymentUpdateRecord = creditCardPaymentUpdateRecord;
    }

    public int getCreditCardPaymentErrorRecordsId() {
        return creditCardPaymentErrorRecordsId;
    }

    public void setCreditCardPaymentErrorRecordsId(int creditCardPaymentErrorRecordsId) {
        this.creditCardPaymentErrorRecordsId = creditCardPaymentErrorRecordsId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public com.spring.starter.model.CreditCardPaymentUpdateRecord getCreditCardPaymentUpdateRecord() {
        return CreditCardPaymentUpdateRecord;
    }

    public void setCreditCardPaymentUpdateRecord(com.spring.starter.model.CreditCardPaymentUpdateRecord creditCardPaymentUpdateRecord) {
        CreditCardPaymentUpdateRecord = creditCardPaymentUpdateRecord;
    }
}
