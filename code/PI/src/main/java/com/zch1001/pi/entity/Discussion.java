package com.zch1001.pi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer discussionId;//讨论帖ID

    private String discussionTitle;//标题

    private String discussionContent;//讨论帖内容

    private LocalDateTime createdTime;  //创建时间
    private Integer posterId;//发帖者id
    private Integer posterRole;//发帖者角色
    private Long courseId;  // 课程ID


    public Long getCourseId() {
        return courseId;
    }

    public Integer getDiscussionId() {
        return discussionId;
    }

    public String getDiscussionTitle() {
        return discussionTitle;
    }

    public String getDiscussionContent() {
        return discussionContent;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public Integer getPosterRole() {
        return posterRole;
    }

    public Integer getPosterId() {
        return posterId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setDiscussionTitle(String discussionTitle) {
        this.discussionTitle = discussionTitle;
    }

    public void setDiscussionContent(String discussionContent) {
        this.discussionContent = discussionContent;
    }

    public void setCreatedTime(LocalDateTime localDateTime) {
        this.createdTime = localDateTime;
    }

    public void setPosterId(int i) {
        this.posterId = i;
    }

    public void setPosterRole(int i) {
        this.posterRole = i;
    }
}
