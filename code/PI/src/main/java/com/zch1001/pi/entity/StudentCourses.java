package com.zch1001.pi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class StudentCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 主键ID

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Integer studentId;  // 学生ID


    private Long courseId;  // 课程ID


    private LocalDateTime createdTime;  // 学生加入课程的时间

    // Getters and Setters


    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
