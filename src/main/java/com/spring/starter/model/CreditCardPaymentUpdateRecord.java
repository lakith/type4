package com.spring.starter.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "credit_card_payment_update_record")
public class CreditCardPaymentUpdateRecord {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int creditCardPaymentUpdateRecordId;

    private String signatureUrl;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "customer_transactions_request_id")
    private CustomerTransactionRequest customerTransactionRequest;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_payment_error_records")
    private List<CreditCardPaymentErrorRecords> creditCardPaymentErrorRecords;

    public CreditCardPaymentUpdateRecord() {
    }

    public CreditCardPaymentUpdateRecord(String signatureUrl, String comment, CustomerTransactionRequest customerTransactionRequest, List<CreditCardPaymentErrorRecords> creditCardPaymentErrorRecords) {
        this.signatureUrl = signatureUrl;
        this.comment = comment;
        this.customerTransactionRequest = customerTransactionRequest;
        this.creditCardPaymentErrorRecords = creditCardPaymentErrorRecords;
    }

    public int getCreditCardPaymentUpdateRecordId() {
        return creditCardPaymentUpdateRecordId;
    }

    public void setCreditCardPaymentUpdateRecordId(int creditCardPaymentUpdateRecordId) {
        this.creditCardPaymentUpdateRecordId = creditCardPaymentUpdateRecordId;
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

    public List<CreditCardPaymentErrorRecords> getCreditCardPaymentErrorRecords() {
        return creditCardPaymentErrorRecords;
    }

    public void setCreditCardPaymentErrorRecords(List<CreditCardPaymentErrorRecords> creditCardPaymentErrorRecords) {
        this.creditCardPaymentErrorRecords = creditCardPaymentErrorRecords;
    }
}
