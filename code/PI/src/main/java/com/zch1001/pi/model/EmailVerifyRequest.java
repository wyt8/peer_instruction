package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailVerifyRequest {
    @JsonProperty("role")
    private int role;

    @JsonProperty("email")
    private String email;


    public int getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }
}
