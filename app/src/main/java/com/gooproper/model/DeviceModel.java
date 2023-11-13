package com.gooproper.model;

public class DeviceModel {
    String Token;
    public DeviceModel(){}
    public DeviceModel(String Token) {
        this.Token = Token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
