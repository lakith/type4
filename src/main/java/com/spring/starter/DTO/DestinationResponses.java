package com.spring.starter.DTO;

public class DestinationResponses {

            private String statusCode;
            private String timeStamp;
            private String address;
            private String statusDetail;
            private String messageId;

    public DestinationResponses() {
    }

    public DestinationResponses(String statusCode, String timeStamp, String address, String statusDetail, String messageId) {
        this.statusCode = statusCode;
        this.timeStamp = timeStamp;
        this.address = address;
        this.statusDetail = statusDetail;
        this.messageId = messageId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
