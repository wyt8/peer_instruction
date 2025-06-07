package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zch1001.pi.entity.Notice;

import java.time.LocalDateTime;
import java.util.List;

public class ReadNoticeResponse {
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
        @JsonProperty("success")
        private boolean success;

        @JsonProperty("content")
        private String content;

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
