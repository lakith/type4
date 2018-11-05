package com.spring.starter.DTO;

import javax.validation.constraints.*;
import java.io.Serializable;

public class AtmOrDebitCardRequestDTO implements Serializable {

    @NotNull
    private int atmOrDebitRequestId;
    @NotNull
    @Min(2)
    private long cardNumber;
    @NotNull
    @NotEmpty
    @Size(min = 2)
    @Pattern(regexp = "^(123|124|125)$")
    private String requestType;

    @NotNull
    private int customerServiceRequestId;

    public AtmOrDebitCardRequestDTO() {
    }

    public AtmOrDebitCardRequestDTO(long cardNumber, String requestType, int customerServiceRequestId) {
        this.cardNumber = cardNumber;
        this.requestType = requestType;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public AtmOrDebitCardRequestDTO(int atmOrDebitRequestId, long cardNumber, String requestType, int customerServiceRequestId) {
        this.atmOrDebitRequestId = atmOrDebitRequestId;
        this.cardNumber = cardNumber;
        this.requestType = requestType;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public int getAtmOrDebitRequestId() {
        return atmOrDebitRequestId;
    }

    public void setAtmOrDebitRequestId(int atmOrDebitRequestId) {
        this.atmOrDebitRequestId = atmOrDebitRequestId;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}
