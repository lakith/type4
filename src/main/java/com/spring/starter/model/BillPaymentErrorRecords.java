package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "bill_payment_error_eecords")
public class BillPaymentErrorRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billPaymentErrorRecords;

    private String oldValue;

    private String newValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_payment_update_records")
    @JsonIgnore
    private BillPaymentUpdateRecords billPaymentUpdateRecords;

    public BillPaymentErrorRecords() {
    }

    public BillPaymentErrorRecords(String oldValue, String newValue, BillPaymentUpdateRecords billPaymentUpdateRecords) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.billPaymentUpdateRecords = billPaymentUpdateRecords;
    }

    public int getBillPaymentErrorRecords() {
        return billPaymentErrorRecords;
    }

    public void setBillPaymentErrorRecords(int billPaymentErrorRecords) {
        this.billPaymentErrorRecords = billPaymentErrorRecords;
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

    public BillPaymentUpdateRecords getBillPaymentUpdateRecords() {
        return billPaymentUpdateRecords;
    }

    public void setBillPaymentUpdateRecords(BillPaymentUpdateRecords billPaymentUpdateRecords) {
        this.billPaymentUpdateRecords = billPaymentUpdateRecords;
    }
}
