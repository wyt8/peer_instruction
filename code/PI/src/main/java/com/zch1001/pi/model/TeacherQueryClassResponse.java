package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class TeacherQueryClassResponse {
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
        @JsonProperty("classes")
        private List<ClassInfo> classInfoList;

        public void setClassInfoList(List<ClassInfo> classInfoList) {
            this.classInfoList = classInfoList;
        }
    }

    public class ClassInfo {
        @JsonProperty("course_id")
        private Long courseId;

        @JsonProperty("class_id")
        private Integer classId;

        @JsonProperty("class_name")
        private String className;

        @JsonProperty("start_time")
        private ZonedDateTime startTime;

        @JsonProperty("status")
        private Integer status;

        @JsonProperty("end_time")
        private ZonedDateTime endTime;

        public void setCourseId(Long courseId) {
            this.courseId = courseId;
        }

        public void setClassId(Integer classId) {
            this.classId = classId;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public void setStartTime(ZonedDateTime startTime) {
            this.startTime = startTime;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public void setEndTime(ZonedDateTime endTime) {
            this.endTime = endTime;
        }

    }
}
