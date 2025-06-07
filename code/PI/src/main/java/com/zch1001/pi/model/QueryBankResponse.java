package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QueryBankResponse {
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
        @JsonProperty("bank_list")
        private List<BankInfo> bankList;

        public void setBankList(List<BankInfo> bankList) {
            this.bankList = bankList;
        }
    }

    public class BankInfo {
        @JsonProperty("bank_id")
        private int id;
        @JsonProperty("bank_name")
        private String name;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
