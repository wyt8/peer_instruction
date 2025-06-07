package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinClassRequest {
    @JsonProperty("data")
    private String data;

    public String getData() {
        return this.data;
    }
}
