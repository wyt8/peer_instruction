package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zch1001.pi.entity.Student;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class QueryStudentResponse {
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
        @JsonProperty("students")
        List<StudentInfo> studentInfoList;

        public void setStudentInfoList(List<StudentInfo> studentInfoList) {
            this.studentInfoList = studentInfoList;
        }
    }

    public class StudentInfo {
        @JsonProperty("student_id")
        private int studentId;

        @JsonProperty("student_name")
        private String studentName;

        @JsonProperty("is_signed")
        private boolean isSigned;

        @JsonProperty("exercise_num")
        private int exerciseNum;

        @JsonProperty("score")
        private int score;

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public void setSigned(boolean signed) {
            isSigned = signed;
        }

        public void setExerciseNum(int exerciseNum) {
            this.exerciseNum = exerciseNum;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
