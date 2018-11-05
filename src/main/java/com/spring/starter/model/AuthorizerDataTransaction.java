package com.spring.starter.model;

import javax.persistence.*;

@Entity
@Table(name = "authorizer_data_transaction")
public class AuthorizerDataTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorizerDataTransactio;

    @OneToOne
    @JoinColumn(name = "staffUserId")
    private StaffUser staffUser;

    private String status;

    private String comment;

    public AuthorizerDataTransaction() {
    }

    public AuthorizerDataTransaction(StaffUser staffUser, String status, String comment) {
        this.staffUser = staffUser;
        this.status = status;
        this.comment = comment;
    }

    public int getAuthorizerDataTransactio() {
        return authorizerDataTransactio;
    }

    public void setAuthorizerDataTransactio(int authorizerDataTransactio) {
        this.authorizerDataTransactio = authorizerDataTransactio;
    }

    public StaffUser getStaffUser() {
        return staffUser;
    }

    public void setStaffUser(StaffUser staffUser) {
        this.staffUser = staffUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
