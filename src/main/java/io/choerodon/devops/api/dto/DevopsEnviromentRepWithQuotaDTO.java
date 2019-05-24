package io.choerodon.devops.api.dto;


import java.util.List;

/**
 * Created by younger on 2018/4/9.
 */
public class DevopsEnviromentRepWithQuotaDTO {
    private Long id;
    private String name;
    private String description;
    private String code;
    private Boolean isActive;
    private Boolean isConnected;
    private Long sequence;
    private Long devopsEnvGroupId;
    private Boolean permission;
    private List<DevopsEnvQuotaDTO> quotas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getConnect() {
        return isConnected;
    }

    public void setConnect(Boolean connect) {
        isConnected = connect;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDevopsEnvGroupId() {
        return devopsEnvGroupId;
    }

    public void setDevopsEnvGroupId(Long devopsEnvGroupId) {
        this.devopsEnvGroupId = devopsEnvGroupId;
    }

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }

    public List<DevopsEnvQuotaDTO> getQuotas() {
        return quotas;
    }

    public void setQuotas(List<DevopsEnvQuotaDTO> quotas) {
        this.quotas = quotas;
    }
}
