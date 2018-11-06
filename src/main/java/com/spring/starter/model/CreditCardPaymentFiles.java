package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "credit_card_payment_files")
public class CreditCardPaymentFiles {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int creditCardPaymentFilesId;

    private String fileType;

    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crediitCardPeymentId")
    @JsonIgnore
    private CrediitCardPeyment crediitCardPeyment;

    public CreditCardPaymentFiles() {
    }

    public CreditCardPaymentFiles(String fileType, String fileUrl, CrediitCardPeyment crediitCardPeyment) {
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.crediitCardPeyment = crediitCardPeyment;
    }

    public int getCreditCardPaymentFilesId() {
        return creditCardPaymentFilesId;
    }

    public void setCreditCardPaymentFilesId(int creditCardPaymentFilesId) {
        this.creditCardPaymentFilesId = creditCardPaymentFilesId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public CrediitCardPeyment getCrediitCardPeyment() {
        return crediitCardPeyment;
    }

    public void setCrediitCardPeyment(CrediitCardPeyment crediitCardPeyment) {
        this.crediitCardPeyment = crediitCardPeyment;
    }
}
