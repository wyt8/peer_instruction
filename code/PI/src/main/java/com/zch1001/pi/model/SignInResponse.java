package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class SignInResponse {
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
        @JsonProperty("is_attended")
        private boolean isAttended;

        @JsonProperty("class_id")
        private int classId;

        @JsonProperty("class_name")
        private String className;

        @JsonProperty("start_time")
        private ZonedDateTime startTime;

        public void setAttended(boolean attended) {
            isAttended = attended;
        }

        public void setClassId(int classId) {
            this.classId = classId;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public void setStartTime(ZonedDateTime startTime) {
            this.startTime = startTime;
        }
    }
}
