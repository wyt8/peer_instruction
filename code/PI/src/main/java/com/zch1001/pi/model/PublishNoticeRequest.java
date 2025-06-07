package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PublishNoticeRequest {
    @JsonProperty("notice")
    private NoticeInfo noticeInfo;

    public class NoticeInfo {
        @JsonProperty("content")
        String content;

        public String getContent() {
            return content;
        }
    }

    public NoticeInfo getNoticeInfo() {
        return noticeInfo;
    }

}
