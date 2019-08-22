package io.choerodon.devops.api.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import io.choerodon.devops.infra.dto.harbor.User;

/**
 * Creator: ChangpingShi0213@gmail.com
 * Date:  10:59 2019/8/8
 * Description:
 */
public class AppMarketUploadVO {
    @ApiModelProperty("市场应用Id")
    private Long mktAppId;

    @ApiModelProperty("应用Id")
    private Long appId;

    @ApiModelProperty("projectId")
    private Long projectId;

    @ApiModelProperty("应用code")
    private String appCode;

    @ApiModelProperty("应用服务")
    private List<AppServiceUploadVO> appServiceUploadVOS;

    @ApiModelProperty("状态: deploy_only,download_only,all")
    private String status;

    @ApiModelProperty("Iam用户Id")
    private Long iamUserId;

    @ApiModelProperty("Harbor用户")
    private User user;

    @ApiModelProperty("SAAS平台getaway URL")
    private String saasGetawayUrl;

    @ApiModelProperty("应用版本")
    private String appVersion;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getIamUserId() {
        return iamUserId;
    }

    public void setIamUserId(Long iamUserId) {
        this.iamUserId = iamUserId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public List<AppServiceUploadVO> getAppServiceUploadVOS() {
        return appServiceUploadVOS;
    }

    public void setAppServiceUploadVOS(List<AppServiceUploadVO> appServiceUploadVOS) {
        this.appServiceUploadVOS = appServiceUploadVOS;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSaasGetawayUrl() {
        return saasGetawayUrl;
    }

    public void setSaasGetawayUrl(String saasGetawayUrl) {
        this.saasGetawayUrl = saasGetawayUrl;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Long getMktAppId() {
        return mktAppId;
    }

    public void setMktAppId(Long mktAppId) {
        this.mktAppId = mktAppId;
    }
}
