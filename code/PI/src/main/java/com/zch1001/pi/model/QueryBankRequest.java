package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryBankRequest {
    @JsonProperty
    private Integer teacherId;

    public Integer getTeacherId() {
        return teacherId;
    }
}


