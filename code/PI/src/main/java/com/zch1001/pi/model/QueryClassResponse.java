package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class QueryClassResponse {
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
        private List<ClassInfo> classList;

        public void setClassList(List<ClassInfo> classList) {
            this.classList = classList;
        }
    }

    public class ClassInfo {
        @JsonProperty("class_id")
        private int id;

        @JsonProperty("class_name")
        private String name;

        @JsonProperty("start_time")
        private ZonedDateTime startTime;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setStartTime(ZonedDateTime startTime) {
            this.startTime = startTime;
        }
    }
}
