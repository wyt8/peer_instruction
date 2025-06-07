package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zch1001.pi.entity.Course;
import com.zch1001.pi.entity.Notice;

import java.time.LocalDateTime;
import java.util.List;

public class QueryNoticeResponse {
    @JsonProperty("code")
    private int code;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("data")
    private Data data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @JsonProperty("notices")
        private List<NoticeInfo> noticeInfoList;

        public void setNoticeInfoList(List<NoticeInfo> noticeInfoList) {
            this.noticeInfoList = noticeInfoList;
        }
    }

    public class NoticeInfo {
        @JsonProperty("notice_id")
        private Integer noticeId;

        @JsonProperty("content")
        private String content;

        @JsonProperty("send_time")
        private LocalDateTime sendTime;

        @JsonProperty("is_received")
        private Boolean isReceived;

        @JsonProperty("course")
        private CourseInfo courseInfo;

        public void setNoticeId(Integer noticeId) {
            this.noticeId = noticeId;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setSendTime(LocalDateTime sendTime) {
            this.sendTime = sendTime;
        }

        public void setReceived(Boolean received) {
            isReceived = received;
        }

        public void setCourseInfo(CourseInfo courseInfo) {
            this.courseInfo = courseInfo;
        }
    }

    public class CourseInfo {
        @JsonProperty("course_id")
        private Long courseId;

        @JsonProperty("course_name")
        private String courseName;

        @JsonProperty("course_image_url")
        private String courseImageUrl;

        @JsonProperty("join_time")
        private LocalDateTime joinTime;

        @JsonProperty("teacher")
        private TeacherInfo teacher;

        public void setCourseId(Long courseId) {
            this.courseId = courseId;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public void setCourseImageUrl(String courseImageUrl) {
            this.courseImageUrl = courseImageUrl;
        }

        public void setJoinTime(LocalDateTime joinTime) {
            this.joinTime = joinTime;
        }

        public void setTeacher(TeacherInfo teacher) {
            this.teacher = teacher;
        }
    }

    public class TeacherInfo {
        @JsonProperty("teacher_id")
        private Integer teacherId;

        @JsonProperty("teacher_name")
        private String teacherName;

        @JsonProperty("teacher_avatar")
        private String teacherAvatar;

        public void setTeacherId(Integer teacherId) {
            this.teacherId = teacherId;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public void setTeacherAvatar(String teacherAvatar) {
            this.teacherAvatar = teacherAvatar;
        }
    }

}
