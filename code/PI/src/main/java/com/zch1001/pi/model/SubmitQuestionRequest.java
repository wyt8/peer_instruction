package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SubmitQuestionRequest {
    @JsonProperty("option")
    private int option;

    @JsonProperty("index")
    private int index;

    public int getOption() {
        return option;
    }

    public int getIndex() {
        return index;
    }
}
