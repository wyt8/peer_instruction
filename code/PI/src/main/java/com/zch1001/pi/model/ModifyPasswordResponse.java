package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyPasswordResponse {

    @JsonProperty("code")
    private int code;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("data")
    private UserData data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public class UserData {
        @JsonProperty("user_id")
        private int userId;

        @JsonProperty("user_name")
        private String userName;

        @JsonProperty("user_avatar")
        private String userAvatar;

        @JsonProperty("token")
        private String token;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
