package com.spring.starter.DTO;

import java.util.List;

public class TifGenarateDTO {

    private int customerTransactionRequestId;
    private String transactionRequestName;
    private String identification;
    private String epfNumber;
    private String name;
    private String signatureURL;
    private List<CustomerFilesDTO> list;

    public TifGenarateDTO() {
    }

    public TifGenarateDTO(int customerTransactionRequestId, String transactionRequestName, String identification, String epfNumber, String name, String signatureURL, List<CustomerFilesDTO> list) {
        this.customerTransactionRequestId = customerTransactionRequestId;
        this.transactionRequestName = transactionRequestName;
        this.identification = identification;
        this.epfNumber = epfNumber;
        this.name = name;
        this.signatureURL = signatureURL;
        this.list = list;
    }

    public int getCustomerTransactionRequestId() {
        return customerTransactionRequestId;
    }

    public void setCustomerTransactionRequestId(int customerTransactionRequestId) {
        this.customerTransactionRequestId = customerTransactionRequestId;
    }

    public String getTransactionRequestName() {
        return transactionRequestName;
    }

    public void setTransactionRequestName(String transactionRequestName) {
        this.transactionRequestName = transactionRequestName;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getEpfNumber() {
        return epfNumber;
    }

    public void setEpfNumber(String epfNumber) {
        this.epfNumber = epfNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignatureURL() {
        return signatureURL;
    }

    public void setSignatureURL(String signatureURL) {
        this.signatureURL = signatureURL;
    }

    public List<CustomerFilesDTO> getList() {
        return list;
    }

    public void setList(List<CustomerFilesDTO> list) {
        this.list = list;
    }
}
