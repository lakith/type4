package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "cash_deposit_breakDown")
public class CashDepositBreakDown {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cashDepositBreakDownId;

    private String comment;

    private int valueOf5000Notes;

    private int valueOf2000Notes;

    private int valueof1000Notes;

    private int valueOf500Notes;

    private int valueOf100Notes;

    private int valueOf50Notes;

    private int valueOf20Notes;

    private int valueOf10Notes;

    private double valueOfcoins;

    private double amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashDepositId")
    @JsonIgnore
    private CashDeposit cashDeposit;

    public CashDepositBreakDown() {
    }

    public CashDepositBreakDown(String comment, int valueOf5000Notes, int valueOf2000Notes, int valueof1000Notes, int valueOf500Notes, int valueOf100Notes, int valueOf50Notes, int valueOf20Notes, int valueOf10Notes, double valueOfcoins, double amount, CashDeposit cashDeposit) {
        this.comment = comment;
        this.valueOf5000Notes = valueOf5000Notes;
        this.valueOf2000Notes = valueOf2000Notes;
        this.valueof1000Notes = valueof1000Notes;
        this.valueOf500Notes = valueOf500Notes;
        this.valueOf100Notes = valueOf100Notes;
        this.valueOf50Notes = valueOf50Notes;
        this.valueOf20Notes = valueOf20Notes;
        this.valueOf10Notes = valueOf10Notes;
        this.valueOfcoins = valueOfcoins;
        this.amount = amount;
        this.cashDeposit = cashDeposit;
    }

    public int getCashDepositBreakDownId() {
        return cashDepositBreakDownId;
    }

    public void setCashDepositBreakDownId(int cashDepositBreakDownId) {
        this.cashDepositBreakDownId = cashDepositBreakDownId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public CashDeposit getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(CashDeposit cashDeposit) {
        this.cashDeposit = cashDeposit;
    }
}
