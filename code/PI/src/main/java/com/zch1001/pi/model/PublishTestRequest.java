package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PublishTestRequest {
    @JsonProperty("exercise_id")
    private Integer testId;

    @JsonProperty("class_id")
    private Integer classId;

    @JsonProperty("test_id")
    private Integer index;

    public Integer getTestId() {
        return testId;
    }

    public Integer getClassId() {
        return classId;
    }

    public Integer getIndex() {
        return index;
    }
}
