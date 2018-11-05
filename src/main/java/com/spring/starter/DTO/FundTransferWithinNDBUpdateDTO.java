package com.spring.starter.DTO;

import com.spring.starter.model.CustomerTransactionRequest;

import java.util.List;

public class FundTransferWithinNDBUpdateDTO {

    private int fundTransferWithinNDBUpdateRecordsId;
    private String signatureUrl;
    private String comment;
    private CustomerTransactionRequest customerTransactionRequest;
    private List<UpdateRecordsListDTO> list;

    public FundTransferWithinNDBUpdateDTO() {
    }

    public FundTransferWithinNDBUpdateDTO(int fundTransferWithinNDBUpdateRecordsId, String signatureUrl, String comment,
                                          CustomerTransactionRequest customerTransactionRequest, List<UpdateRecordsListDTO> list) {
        this.fundTransferWithinNDBUpdateRecordsId = fundTransferWithinNDBUpdateRecordsId;
        this.signatureUrl = signatureUrl;
        this.comment = comment;
        this.customerTransactionRequest = customerTransactionRequest;
        this.list = list;
    }

    public int getFundTransferWithinNDBUpdateRecordsId() {
        return fundTransferWithinNDBUpdateRecordsId;
    }

    public void setFundTransferWithinNDBUpdateRecordsId(int fundTransferWithinNDBUpdateRecordsId) {
        this.fundTransferWithinNDBUpdateRecordsId = fundTransferWithinNDBUpdateRecordsId;
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
