package com.spring.starter.DTO;

public class UpdateRecordsListDTO {

    private ErrorRecordsView oldValue;
    private ErrorRecordsView newValue;

    public UpdateRecordsListDTO() {
    }

    public ErrorRecordsView getOldValue() {
        return oldValue;
    }

    public void setOldValue(ErrorRecordsView oldValue) {
        this.oldValue = oldValue;
    }

    public ErrorRecordsView getNewValue() {
        return newValue;
    }

    public void setNewValue(ErrorRecordsView newValue) {
        this.newValue = newValue;
    }

    public UpdateRecordsListDTO(ErrorRecordsView oldValue, ErrorRecordsView newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
