package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyStudentInfoRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("password")
    private String password;

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPassword() {
        return password;
    }

}
