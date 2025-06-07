package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassDetailRequest {
    @JsonProperty("class_id")
    private Integer classId;

    public Integer getClassId() {
        return classId;
    }
}


