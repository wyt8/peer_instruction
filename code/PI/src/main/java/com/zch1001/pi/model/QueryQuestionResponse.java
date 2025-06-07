package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class QueryQuestionResponse {
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
        @JsonProperty("questions_list")
        private List<questionInfo> questionsList;

        public void setQuestionsList(List<questionInfo> questionsList) {
            this.questionsList = questionsList;
        }
    }

    public class questionInfo {
        @JsonProperty("bank_id")
        private int bank_id;

        @JsonProperty("question_id")
        private int id;

        @JsonProperty("quName")
        private String name;

        @JsonProperty("content")
        private String content;

        @JsonProperty("quOptions")
        private List<String> options;

        @JsonProperty("quAnswer")
        private String answer;

        @JsonProperty("scorevalue")
        private Integer score;

        @JsonProperty("quType")
        private String type;

        public void setBank_id(int bank_id) {
            this.bank_id = bank_id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public void setOptions(List<String> options) {
            this.options = options;
        }
        public void setAnswer(String answer) {
            this.answer = answer;
        }
        public void setScore(Integer score) {
            this.score = score;
        }
        public void setType(String type) {
            this.type = type;
        }
    }
}
