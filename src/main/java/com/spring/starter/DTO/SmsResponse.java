package com.spring.starter.DTO;

public class SmsResponse {
        private String statusCode;
        private String requestId;
        private String statusDetail;
        private DestinationResponses destinationResponses;
        private String version;

    public SmsResponse() {
    }

    public SmsResponse(String statusCode, String requestId, String statusDetail, DestinationResponses destinationResponses, String version) {
        this.statusCode = statusCode;
        this.requestId = requestId;
        this.statusDetail = statusDetail;
        this.destinationResponses = destinationResponses;
        this.version = version;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public DestinationResponses getDestinationResponses() {
        return destinationResponses;
    }

    public void setDestinationResponses(DestinationResponses destinationResponses) {
        this.destinationResponses = destinationResponses;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
