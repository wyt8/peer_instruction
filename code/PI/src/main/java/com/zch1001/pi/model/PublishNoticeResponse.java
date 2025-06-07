package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class PublishNoticeResponse {
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
        @JsonProperty("notice_id")
        private Integer noticeId;

        @JsonProperty("content")
        private String content;

        @JsonProperty("send_time")
        private LocalDateTime sendTime;

        public void setContent(String content) {
            this.content = content;
        }

        public void setSendTime(LocalDateTime sendTime) {
            this.sendTime = sendTime;
        }

        public void setNoticeId(Integer noticeId) {
            this.noticeId = noticeId;
        }
    }

}
