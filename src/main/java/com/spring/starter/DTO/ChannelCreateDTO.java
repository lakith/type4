package com.spring.starter.DTO;

public class ChannelCreateDTO {

    private String username;

    private String clientKey;

    private String browserKey;

    public ChannelCreateDTO() {
    }

    public ChannelCreateDTO(String username, String clientKey, String browserKey) {
        this.username = username;
        this.clientKey = clientKey;
        this.browserKey = browserKey;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getBrowserKey() {
        return browserKey;
    }

    public void setBrowserKey(String browserKey) {
        this.browserKey = browserKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
