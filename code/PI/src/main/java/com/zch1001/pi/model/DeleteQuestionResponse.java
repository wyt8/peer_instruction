package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DeleteQuestionResponse {
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
    }

}
