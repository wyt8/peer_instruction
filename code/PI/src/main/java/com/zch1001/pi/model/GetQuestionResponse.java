package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


public class GetQuestionResponse {
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
        @JsonProperty("exercises")
        private List<TestInfo> testInfoList;

        public void setTestInfoList(List<TestInfo> testInfoList) {
            this.testInfoList = testInfoList;
        }
    }

    public class TestInfo {
        @JsonProperty("exercise_id")
        private Integer testId;

        @JsonProperty("content")
        private String content;

        @JsonProperty("options")
        private List<String> options;

        @JsonProperty("first_answer")
        private AnswerInfo firstAnswer;

        @JsonProperty("second_answer")
        private AnswerInfo secondAnswer;

        public void setTestId(Integer testId) {
            this.testId = testId;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }

        public void setFirstAnswer(AnswerInfo firstAnswer) {
            this.firstAnswer = firstAnswer;
        }

        public void setSecondAnswer(AnswerInfo secondAnswer) {
            this.secondAnswer = secondAnswer;
        }

        public AnswerInfo getFirstAnswer() {
            return firstAnswer;
        }

        public AnswerInfo getSecondAnswer() {
            return secondAnswer;
        }
    }



    public class AnswerInfo {
        @JsonProperty("status")
        private Integer status;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("start_time")
        private ZonedDateTime startTime;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("limit_time")
        private Integer limitTime;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("my_option")
        private Integer myOption;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("right_option")
        private Integer rightOption;

        public void setStatus(Integer status) {
            this.status = status;
        }

        public void setStartTime(ZonedDateTime startTime) {
            this.startTime = startTime;
        }

        public void setLimitTime(Integer limitTime) {
            this.limitTime = limitTime;
        }

        public void setMyOption(Integer myOption) {
            this.myOption = myOption;
        }

        public void setRightOption(Integer rightOption) {
            this.rightOption = rightOption;
        }
    }
}
