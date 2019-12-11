package io.choerodon.devops.api.dto;

public class DuckulaRep {

    int code = 0;

    String baseUrl = "";

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
