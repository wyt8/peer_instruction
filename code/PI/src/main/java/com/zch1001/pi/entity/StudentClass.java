package com.zch1001.pi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class StudentClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//ID

    private Integer studentId;//学生id

    private Integer classId;//课程iD

    private LocalDateTime createdTime;//创建时间

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public void setCreatedTime(LocalDateTime localDateTime) {
        this.createdTime = localDateTime;
    }

    public Integer getStudentId() {
        return studentId;
    }
}
