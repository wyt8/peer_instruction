package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TeacherQueryClassRequest {
    @JsonProperty("course_id")
    private Long courseId;

    public Long getCourseId() {
        return courseId;
    }
}
