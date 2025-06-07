package com.zch1001.pi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;//回复ID
    private String reviewContent;//回复内容
    private Integer discussionId;//关联的讨论
    private Integer reviewerId;  //回复者id
    private Integer reviewerRole;//回复者角色
    private LocalDateTime createdTime;  //发布时间


    public Integer getDiscussionId() {
        return discussionId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public Integer getReviewerRole() {
        return reviewerRole;
    }

    public Integer getReviewerId() {
        return reviewerId;
    }

    public void setDiscussionId(Integer discussionId) {
        this.discussionId = discussionId;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public void setReviewerRole(Integer reviewerRole) {
        this.reviewerRole = reviewerRole;
    }

    public void setReviewerId(Integer reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Integer getReviewId() {
        return reviewId;
    }
}
