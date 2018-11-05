package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name ="bill_payment_update_records")
public class BillPaymentUpdateRecords {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int billPaymentUpdateRecords;

    private String signatureUrl;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "customer_transactions_request_id")
    private CustomerTransactionRequest customerTransactionRequest;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cash_withdrawal_record_error_id")
    private List<BillPaymentErrorRecords> billPaymentErrorRecords;

    public BillPaymentUpdateRecords() {
    }

    public BillPaymentUpdateRecords(String signatureUrl, String comment, CustomerTransactionRequest customerTransactionRequest, List<BillPaymentErrorRecords> billPaymentErrorRecords) {
        this.signatureUrl = signatureUrl;
        this.comment = comment;
        this.customerTransactionRequest = customerTransactionRequest;
        this.billPaymentErrorRecords = billPaymentErrorRecords;
    }

    public int getBillPaymentUpdateRecords() {
        return billPaymentUpdateRecords;
    }

    public void setBillPaymentUpdateRecords(int billPaymentUpdateRecords) {
        this.billPaymentUpdateRecords = billPaymentUpdateRecords;
    }

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(String signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public List<BillPaymentErrorRecords> getBillPaymentErrorRecords() {
        return billPaymentErrorRecords;
    }

    public void setBillPaymentErrorRecords(List<BillPaymentErrorRecords> billPaymentErrorRecords) {
        this.billPaymentErrorRecords = billPaymentErrorRecords;
    }
}
