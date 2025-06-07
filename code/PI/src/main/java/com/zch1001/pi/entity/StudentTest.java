package com.zch1001.pi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Entity
public class StudentTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer testId;  // 测试id
    private Integer studentId;  // 学生id
    private Integer testType;  // 测试类型  1为一次测试，2为二次测试
    private Integer testScore;  // 测试id
    private String answer;  // 答题详情
    private Integer classId;  // 课程id
    private String content;
    private Integer status;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private Integer limitTime;
    private Integer myOption;
    private Integer rightOption;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getTestType() {
        return testType;
    }

    public void setTestType(Integer testType) {
        this.testType = testType;
    }

    public void setTestScore(Integer testScore) {
        this.testScore = testScore;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getTestScore() {
        return testScore;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    public Integer getLimitTime() {
        return limitTime;
    }

    public void setMyOption(Integer myOption) {
        this.myOption = myOption;
    }

    public Integer getMyOption() {
        return myOption;
    }

    public Integer getRightOption() {
        return rightOption;
    }

    public void setRightOption(Integer rightOption) {
        this.rightOption = rightOption;
    }
}
