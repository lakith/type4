package com.spring.starter.DTO;

import com.spring.starter.model.TellerQueue;

public class TellerQueueDTO {

    private String message;

    private TellerQueue tellerQueue;

    public TellerQueueDTO() {
    }

    public TellerQueueDTO(String message, TellerQueue tellerQueue) {
        this.message = message;
        this.tellerQueue = tellerQueue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TellerQueue getTellerQueue() {
        return tellerQueue;
    }

    public void setTellerQueue(TellerQueue tellerQueue) {
        this.tellerQueue = tellerQueue;
    }
}
