package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class ClassDetailResponse {
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
        private ClassInfo classInfo;

        public void setClassInfo(ClassInfo classInfo) {
            this.classInfo = classInfo;
        }
    }

    public class ClassInfo {
        @JsonProperty("course_id")
        private Long courseId;

        @JsonProperty("class_id")
        private Integer classId;

        @JsonProperty("class_name")
        private String className;

        @JsonProperty("teacher_id")
        private Long teacherId;

        @JsonProperty("teacher_name")
        private String teacherName;

        @JsonProperty("sign_num")
        private Integer signNum;

        @JsonProperty("examing_num")
        private Integer examingNum;

        @JsonProperty("finished_num")
        private Integer finishedNum;

        @JsonProperty("start_time")
        private ZonedDateTime startTime;

        @JsonProperty("status")
        private Integer status;

        @JsonProperty("end_time")
        private ZonedDateTime endTime;

        @JsonProperty("exercise_list")
        private List<Integer> exerciseList;

        public void setCourseId(Long courseId) {
            this.courseId = courseId;
        }

        public void setClassId(Integer classId) {
            this.classId = classId;
        }


        public void setClassName(String className) {
            this.className = className;
        }

        public void setTeacherId(Long teacherId) {
            this.teacherId = teacherId;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public void setSignNum(Integer signNum) {
            this.signNum = signNum;
        }

        public void setExamingNum(Integer examingNum) {
            this.examingNum = examingNum;
        }

        public void setFinishedNum(Integer finishedNum) {
            this.finishedNum = finishedNum;
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

        public void setExerciseList(List<Integer> exerciseList) {
            this.exerciseList = exerciseList;
        }
    }
}
