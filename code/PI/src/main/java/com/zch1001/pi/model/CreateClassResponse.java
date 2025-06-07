package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

public class CreateClassResponse {
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
        @JsonProperty("class_id")
        private Integer classId;

        @JsonProperty("student_id")
        private List<Integer> studentId;

        @JsonProperty("student_name")
        private List<String> studentName;

        @JsonProperty("createTime")
        private ZonedDateTime createTime;

        @JsonProperty("teacher")
        private TeacherInfo teacherInfo;

        @JsonProperty("course_id")
        private Long courseId;

        public void setClassId(int id) {
            this.classId = id;
        }

        public void setStudentId(List<Integer> id) {
            this.studentId = id;
        }

        public void setStudentName(List<String> name) {
            this.studentName = name;
        }

        public void setCreateTime(ZonedDateTime createTime) {
            this.createTime = createTime;
        }

        public void setTeacherInfo(TeacherInfo teacherInfo) {
            this.teacherInfo = teacherInfo;
        }

        public void setCourseId(Long courseId) {
            this.courseId = courseId;
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
