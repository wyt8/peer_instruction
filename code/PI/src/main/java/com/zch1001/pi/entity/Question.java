package com.zch1001.pi.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    private String questionName;

    private String questionContent;

    @ElementCollection
    private List<String> options;

    private String questionAnswer;

    private Integer questionScore;

    private String questionType;

    private Integer bankId;

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String name) {
        this.questionName = name;
    }


    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String content) {
        this.questionContent = content;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setQuestionOptions(List<String> options) {
        this.options = options;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String answer) {
        this.questionAnswer = answer;
    }

    public Integer getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(Integer score) {
        this.questionScore = score;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String type) {
        this.questionType = type;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bank_id) {
        this.bankId = bank_id;
    }
}
