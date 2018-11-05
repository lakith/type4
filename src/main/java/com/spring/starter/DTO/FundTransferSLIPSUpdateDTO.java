package com.spring.starter.DTO;

import com.spring.starter.model.CustomerTransactionRequest;

import java.util.List;

public class FundTransferSLIPSUpdateDTO {

    private int fundTransferSLIPRecordsId;
    private String signatureUrl;
    private String comment;
    private CustomerTransactionRequest customerTransactionRequest;
    private List<UpdateRecordsListDTO> list;

    public FundTransferSLIPSUpdateDTO() {
    }

    public FundTransferSLIPSUpdateDTO(int fundTransferSLIPRecordsId, String signatureUrl, String comment,
                                      CustomerTransactionRequest customerTransactionRequest, List<UpdateRecordsListDTO> list) {
        this.fundTransferSLIPRecordsId = fundTransferSLIPRecordsId;
        this.signatureUrl = signatureUrl;
        this.comment = comment;
        this.customerTransactionRequest = customerTransactionRequest;
        this.list = list;
    }

    public int getFundTransferSLIPRecordsId() {
        return fundTransferSLIPRecordsId;
    }

    public void setFundTransferSLIPRecordsId(int fundTransferSLIPRecordsId) {
        this.fundTransferSLIPRecordsId = fundTransferSLIPRecordsId;
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

    public List<UpdateRecordsListDTO> getList() {
        return list;
    }

    public void setList(List<UpdateRecordsListDTO> list) {
        this.list = list;
    }
}
