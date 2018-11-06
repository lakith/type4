package com.spring.starter.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

public class FundTransferWithinNDBDTO implements Serializable {

    @NotNull
    private Date date;
    @NotNull
    @Min(value = 2,message = "Insert Valid Account Number To Continue")
    private long fromAccount;

    @NotNull
    @Size(min = 2)
    private String fromAccountName;

    @NotNull
    @Size(min = 2)
    private String fromAccountType;
    @NotNull
    private int currencyId;
    @NotNull
    private double amount;

    @NotNull
    @Size(min = 2)
    private String toAccountName;

    @NotNull
    @Min(value = 2,message = "Insert Valid Account Number To Continue")
    private long toAccount;
    @NotNull
    private int branchId;

    public FundTransferWithinNDBDTO() {
    }

    public FundTransferWithinNDBDTO(@NotNull Date date, @NotNull @Min(value = 2, message = "Insert Valid Account Number To Continue") long fromAccount, @NotNull @Size(min = 2) String fromAccountType, @NotNull int currencyId, @NotNull double amount, @NotNull @Min(value = 2, message = "Insert Valid Account Number To Continue") long toAccount, @NotNull int branchId) {
        this.date = date;
        this.fromAccount = fromAccount;
        this.fromAccountType = fromAccountType;
        this.currencyId = currencyId;
        this.amount = amount;
        this.toAccount = toAccount;
        this.branchId = branchId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getFromAccountType() {
        return fromAccountType;
    }

    public void setFromAccountType(String fromAccountType) {
        this.fromAccountType = fromAccountType;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getToAccount() {
        return toAccount;
    }

    public void setToAccount(long toAccount) {
        this.toAccount = toAccount;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getFromAccountName() {
        return fromAccountName;
    }

    public void setFromAccountName(String fromAccountName) {
        this.fromAccountName = fromAccountName;
    }

    public String getToAccountName() {
        return toAccountName;
    }

    public void setToAccountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }
}
