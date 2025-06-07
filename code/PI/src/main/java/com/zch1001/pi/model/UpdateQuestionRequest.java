package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UpdateQuestionRequest {
    @JsonProperty("bank_id")
    private int bank_id;

    @JsonProperty("quName")
    private String name;

    @JsonProperty("content")
    private String content;

    @JsonProperty("quOptions")
    private List<String> options;

    @JsonProperty("quAnswer")
    private String answer;

    @JsonProperty("scorevalue")
    private int score;

    public int getBank_id() {
        return bank_id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }

    public int getScore() {
        return score;
    }

}
