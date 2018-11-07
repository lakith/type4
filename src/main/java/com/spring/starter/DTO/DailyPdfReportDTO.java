package com.spring.starter.DTO;

import java.util.Date;

public class DailyPdfReportDTO {

    private int customerTransactionRequestId;
    private String digFormType;
    private String identification;
    private Date requestCompleteDate;
    private String epfNumber;
    private String staffName;
    private String QueueNumber;

    public DailyPdfReportDTO() {
    }

    public DailyPdfReportDTO(int customerTransactionRequestId, String digFormType, String identification, Date requestCompleteDate, String epfNumber, String staffName, String queueNumber) {
        this.customerTransactionRequestId = customerTransactionRequestId;
        this.digFormType = digFormType;
        this.identification = identification;
        this.requestCompleteDate = requestCompleteDate;
        this.epfNumber = epfNumber;
        this.staffName = staffName;
        QueueNumber = queueNumber;
    }

    public int getCustomerTransactionRequestId() {
        return customerTransactionRequestId;
    }

    public void setCustomerTransactionRequestId(int customerTransactionRequestId) {
        this.customerTransactionRequestId = customerTransactionRequestId;
    }

    public String getDigFormType() {
        return digFormType;
    }

    public void setDigFormType(String digFormType) {
        this.digFormType = digFormType;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Date getRequestCompleteDate() {
        return requestCompleteDate;
    }

    public void setRequestCompleteDate(Date requestCompleteDate) {
        this.requestCompleteDate = requestCompleteDate;
    }

    public String getEpfNumber() {
        return epfNumber;
    }

    public void setEpfNumber(String epfNumber) {
        this.epfNumber = epfNumber;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getQueueNumber() {
        return QueueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        QueueNumber = queueNumber;
    }
}
