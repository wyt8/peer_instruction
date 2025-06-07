package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class CreateClassRequest {
    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("start_time")
    private ZonedDateTime startTime;

    @JsonProperty("end_time")
    private ZonedDateTime endTime;

    public Long getCourseId() {
        return courseId;
    }

    public String getClassName() {
        return className;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }
}
