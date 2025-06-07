package com.zch1001.pi.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeId;

    private Integer courseId;

    private LocalDateTime noticeTime;

    private String noticeContent;

    @ElementCollection
    private List<Integer> student;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(LocalDateTime noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public List<Integer> getStudent() {
        return student;
    }
}
