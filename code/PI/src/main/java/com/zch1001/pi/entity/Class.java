package com.zch1001.pi.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classId;

    private Long courseId;

    private String className;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private ZonedDateTime createdTime;

    private Integer status;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer id) {
        this.classId = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String name) {
        this.className = name;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long course_id) { this.courseId = course_id; }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
