package io.choerodon.devops.api.vo;

/**
 * Creator: ChangpingShi0213@gmail.com
 * Date:  14:47 2019/7/3
 * Description:
 */
public class AccessTokenVO {
    private String saasMarketUrl;
    private String accessToken;

    public String getSaasMarketUrl() {
        return saasMarketUrl;
    }

    public void setSaasMarketUrl(String saasMarketUrl) {
        this.saasMarketUrl = saasMarketUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}