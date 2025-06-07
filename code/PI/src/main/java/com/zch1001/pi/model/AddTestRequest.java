package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AddTestRequest {
    @JsonProperty("exercise_list")
    private List<Integer> testId;

    @JsonProperty("class_id")
    private Integer classId;

    public List<Integer> getTestId() {
        return testId;
    }

    public Integer getClassId() {
        return classId;
    }
}
