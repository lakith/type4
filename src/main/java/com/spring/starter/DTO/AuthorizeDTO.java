package com.spring.starter.DTO;

public class AuthorizeDTO {

    private boolean authorize;

    public AuthorizeDTO() {
    }

    public AuthorizeDTO(boolean authorize) {
        this.authorize = authorize;
    }

    public boolean isAuthorize() {
        return authorize;
    }

    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }
}
