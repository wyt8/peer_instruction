package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AddTestResponse {

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
        @JsonProperty("exercise_list")
        private List<Integer> exerciseList;

        public void setExerciseList(List<Integer> exerciseList) {
            this.exerciseList = exerciseList;
        }

    }
}
