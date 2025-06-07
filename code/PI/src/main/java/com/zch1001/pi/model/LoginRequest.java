package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {

    @JsonProperty("role")
    private int role;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    public int getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
