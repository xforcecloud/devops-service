package io.choerodon.devops.api.dto;

import java.util.List;

public class DevopsEnvGroupEnvsWithQuotaDTO {
    private Long devopsEnvGroupId;
    private String devopsEnvGroupName;
    private List<DevopsEnviromentRepWithQuotaDTO> devopsEnviromentRepDTOs;

    public Long getDevopsEnvGroupId() {
        return devopsEnvGroupId;
    }

    public void setDevopsEnvGroupId(Long devopsEnvGroupId) {
        this.devopsEnvGroupId = devopsEnvGroupId;
    }

    public String getDevopsEnvGroupName() {
        return devopsEnvGroupName;
    }

    public void setDevopsEnvGroupName(String devopsEnvGroupName) {
        this.devopsEnvGroupName = devopsEnvGroupName;
    }

    public List<DevopsEnviromentRepWithQuotaDTO> getDevopsEnviromentRepDTOs() {
        return devopsEnviromentRepDTOs;
    }

    public void setDevopsEnviromentRepDTOs(List<DevopsEnviromentRepWithQuotaDTO> devopsEnviromentRepDTOs) {
        this.devopsEnviromentRepDTOs = devopsEnviromentRepDTOs;
    }
}
