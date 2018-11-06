package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cash_withdrawal")
public class CashWithdrawal implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int cashWithdrawalId;
    private Date  date;
    private long accountNo;
    private String accountHolder;
    private String currency;
    private double amount;
    private String amountInWords;
    private String identity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashWithDrawalBreakDownID")
    CashWithDrawalBreakDown cashWithDrawalBreakDown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BranchId")
    private  Branch branch;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerTransactionRequestId")
    private CustomerTransactionRequest customerTransactionRequest;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cash_withdrawal_relation_file_id")
    private List<CashWithdrawalFile> cashWithdrawalFile;

    private String signatureUrl;

    private boolean status;

    private Date requestCompleteDate;

    public CashWithdrawal() {
    }

    public CashWithdrawal(Date date, long accountNo, String accountHolder, String currency, double amount, String amountInWords, String identity, CashWithDrawalBreakDown cashWithDrawalBreakDown, Branch branch, CustomerTransactionRequest customerTransactionRequest, List<CashWithdrawalFile> cashWithdrawalFile, String signatureUrl, boolean status, Date requestCompleteDate) {
        this.date = date;
        this.accountNo = accountNo;
        this.accountHolder = accountHolder;
        this.currency = currency;
        this.amount = amount;
        this.amountInWords = amountInWords;
        this.identity = identity;
        this.cashWithDrawalBreakDown = cashWithDrawalBreakDown;
        this.branch = branch;
        this.customerTransactionRequest = customerTransactionRequest;
        this.cashWithdrawalFile = cashWithdrawalFile;
        this.signatureUrl = signatureUrl;
        this.status = status;
        this.requestCompleteDate = requestCompleteDate;
    }

    public List<CashWithdrawalFile> getCashWithdrawalFile() {
        return cashWithdrawalFile;
    }

    public void setCashWithdrawalFile(List<CashWithdrawalFile> cashWithdrawalFile) {
        this.cashWithdrawalFile = cashWithdrawalFile;
    }

    public int getCashWithdrawalId() {
        return cashWithdrawalId;
    }

    public void setCashWithdrawalId(int cashWithdrawalId) {
        this.cashWithdrawalId = cashWithdrawalId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(String signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getRequestCompleteDate() {
        return requestCompleteDate;
    }

    public void setRequestCompleteDate(Date requestCompleteDate) {
        this.requestCompleteDate = requestCompleteDate;
    }

    public CashWithDrawalBreakDown getCashWithDrawalBreakDown() {
        return cashWithDrawalBreakDown;
    }

    public void setCashWithDrawalBreakDown(CashWithDrawalBreakDown cashWithDrawalBreakDown) {
        this.cashWithDrawalBreakDown = cashWithDrawalBreakDown;
    }
}
