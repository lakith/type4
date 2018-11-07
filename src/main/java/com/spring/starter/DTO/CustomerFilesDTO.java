package com.spring.starter.DTO;

public class CustomerFilesDTO {

    private String url;
    private String type;

    public CustomerFilesDTO() {
    }

    public CustomerFilesDTO(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
