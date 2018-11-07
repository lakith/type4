package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bill_payment_cash_break_down")
public class BillPaymentCashBreakDown {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billPaymentCashBreakDownId;

    private String comment;

    private Date date;

    private int valueOf5000Notes;

    private int valueOf2000Notes;

    private int valueof1000Notes;

    private int valueOf500Notes;

    private int valueOf100Notes;

    private int valueOf50Notes;

    private int valueOf20Notes;

    private int valueOf10Notes;

    private double valueOfcoins;

    private double ammount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billPaymentId")
    @JsonIgnore
    private BillPayment billPayment;

    public BillPaymentCashBreakDown() {
    }

    public BillPaymentCashBreakDown(String comment, Date date, int valueOf5000Notes, int valueOf2000Notes, int valueof1000Notes, int valueOf500Notes, int valueOf100Notes, int valueOf50Notes, int valueOf20Notes, int valueOf10Notes, double valueOfcoins, double ammount) {
        this.comment = comment;
        this.date = date;
        this.valueOf5000Notes = valueOf5000Notes;
        this.valueOf2000Notes = valueOf2000Notes;
        this.valueof1000Notes = valueof1000Notes;
        this.valueOf500Notes = valueOf500Notes;
        this.valueOf100Notes = valueOf100Notes;
        this.valueOf50Notes = valueOf50Notes;
        this.valueOf20Notes = valueOf20Notes;
        this.valueOf10Notes = valueOf10Notes;
        this.valueOfcoins = valueOfcoins;
        this.ammount = ammount;
    }

    public int getBillPaymentCashBreakDownId() {
        return billPaymentCashBreakDownId;
    }

    public void setBillPaymentCashBreakDownId(int billPaymentCashBreakDownId) {
        this.billPaymentCashBreakDownId = billPaymentCashBreakDownId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getValueOf5000Notes() {
        return valueOf5000Notes;
    }

    public void setValueOf5000Notes(int valueOf5000Notes) {
        this.valueOf5000Notes = valueOf5000Notes;
    }

    public int getValueOf2000Notes() {
        return valueOf2000Notes;
    }

    public void setValueOf2000Notes(int valueOf2000Notes) {
        this.valueOf2000Notes = valueOf2000Notes;
    }

    public int getValueof1000Notes() {
        return valueof1000Notes;
    }

    public void setValueof1000Notes(int valueof1000Notes) {
        this.valueof1000Notes = valueof1000Notes;
    }

    public int getValueOf500Notes() {
        return valueOf500Notes;
    }

    public void setValueOf500Notes(int valueOf500Notes) {
        this.valueOf500Notes = valueOf500Notes;
    }

    public int getValueOf100Notes() {
        return valueOf100Notes;
    }

    public void setValueOf100Notes(int valueOf100Notes) {
        this.valueOf100Notes = valueOf100Notes;
    }

    public int getValueOf50Notes() {
        return valueOf50Notes;
    }

    public void setValueOf50Notes(int valueOf50Notes) {
        this.valueOf50Notes = valueOf50Notes;
    }

    public int getValueOf20Notes() {
        return valueOf20Notes;
    }

    public void setValueOf20Notes(int valueOf20Notes) {
        this.valueOf20Notes = valueOf20Notes;
    }

    public int getValueOf10Notes() {
        return valueOf10Notes;
    }

    public void setValueOf10Notes(int valueOf10Notes) {
        this.valueOf10Notes = valueOf10Notes;
    }

    public double getValueOfcoins() {
        return valueOfcoins;
    }

    public void setValueOfcoins(double valueOfcoins) {
        this.valueOfcoins = valueOfcoins;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public BillPayment getBillPayment() {
        return billPayment;
    }

    public void setBillPayment(BillPayment billPayment) {
        this.billPayment = billPayment;
    }
}
