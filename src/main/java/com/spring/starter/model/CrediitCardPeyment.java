package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "crediit_card_peyment")
public class CrediitCardPeyment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int crediitCardPeymentId;

        @NotNull
        @NotEmpty
        @Size(min = 9, max = 10,message = "mobile number must be between 9 and 10")
        @Pattern(regexp = "^([+0-9])*$")
        private String telephoneNumber;

        @NotNull
        @Pattern(regexp = "^(CASH|CHEQUE)$")
        private String paymenntMethod;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "bankId")
        private Bank bank;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "branchId")
        private Branch branch;

        private  String chequenumber;

        private double ammount;

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

        @NotNull
        private double total;

        private String signatureUrl;

        private Date requestCompleteDate;

        private boolean status;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customerTransactionRequestId")
        private CustomerTransactionRequest customerTransactionRequest;

        public CrediitCardPeyment(@NotNull @NotEmpty @Size(min = 9, max = 10, message = "mobile number must be between 9 and 10") @Pattern(regexp = "^([+0-9])*$") String telephoneNumber, @NotNull @Pattern(regexp = "^(CASH|CHEQUE)$") String paymenntMethod, Bank bank, Branch branch, String chequenumber, double ammount, Date date, int valueOf5000Notes, int valueOf2000Notes, int valueof1000Notes, int valueOf500Notes, int valueOf100Notes, int valueOf50Notes, int valueOf20Notes, int valueOf10Notes, double valueOfcoins, @NotNull double total, String signatureUrl, Date requestCompleteDate, boolean status, CustomerTransactionRequest customerTransactionRequest) {
            this.telephoneNumber = telephoneNumber;
            this.paymenntMethod = paymenntMethod;
            this.bank = bank;
            this.branch = branch;
            this.chequenumber = chequenumber;
            this.ammount = ammount;
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
            this.total = total;
            this.signatureUrl = signatureUrl;
            this.requestCompleteDate = requestCompleteDate;
            this.status = status;
            this.customerTransactionRequest = customerTransactionRequest;
        }

        public CrediitCardPeyment() {
        }

    public int getCrediitCardPeymentId() {
        return crediitCardPeymentId;
    }

    public void setCrediitCardPeymentId(int crediitCardPeyment) {
        this.crediitCardPeymentId = crediitCardPeymentId;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getPaymenntMethod() {
        return paymenntMethod;
    }

    public void setPaymenntMethod(String paymenntMethod) {
        this.paymenntMethod = paymenntMethod;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getChequenumber() {
        return chequenumber;
    }

    public void setChequenumber(String chequenumber) {
        this.chequenumber = chequenumber;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(String signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public Date getRequestCompleteDate() {
        return requestCompleteDate;
    }

    public void setRequestCompleteDate(Date requestCompleteDate) {
        this.requestCompleteDate = requestCompleteDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }
}
