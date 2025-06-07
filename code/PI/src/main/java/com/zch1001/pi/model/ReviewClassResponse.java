package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class ReviewClassResponse {
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
        @JsonProperty("review")
        ReviewInfo reviewInfo;

        public void setReviewInfo(ReviewInfo reviewInfo) {
            this.reviewInfo = reviewInfo;
        }
    }

    public class ReviewInfo {
        @JsonProperty("course_id")
        private Long courseId;

        @JsonProperty("class_id")
        private int classId;

        @JsonProperty("class_name")
        private String className;

        @JsonProperty("start_time")
        private ZonedDateTime startTime;

        @JsonProperty("status")
        private Integer status;

        @JsonProperty("end_time")
        private ZonedDateTime endTime;

        @JsonProperty("exercise_list")
        private List<TestInfo> testInfoList;

        @JsonProperty("rate_total")
        private TotalInfo totalInfo;

        public void setCourseId(Long courseId) {
            this.courseId = courseId;
        }

        public void setClassId(int classId) {
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

        public void setTestInfoList(List<TestInfo> testInfoList) {
            this.testInfoList = testInfoList;
        }

        public void setTotalInfo(TotalInfo totalInfo) {
            this.totalInfo = totalInfo;
        }
    }

    public class TestInfo {
        @JsonProperty("exercise_id")
        private Integer testId;

        @JsonProperty("test1_rate")
        private Integer test1Rate;

        @JsonProperty("test2_rate")
        private Integer test2Rate;

        public void setTestId(Integer testId) {
            this.testId = testId;
        }

        public void setTest1Rate(Integer test1Rate) {
            this.test1Rate = test1Rate;
        }

        public void setTest2Rate(Integer test2Rate) {
            this.test2Rate = test2Rate;
        }
    }

    public class TotalInfo {
        @JsonProperty("test1_rate")
        private Integer test1Rate;

        @JsonProperty("test2_rate")
        private Integer test2Rate;

        public void setTest1Rate(Integer test1Rate) {
            this.test1Rate = test1Rate;
        }

        public void setTest2Rate(Integer test2Rate) {
            this.test2Rate = test2Rate;
        }
    }

}
