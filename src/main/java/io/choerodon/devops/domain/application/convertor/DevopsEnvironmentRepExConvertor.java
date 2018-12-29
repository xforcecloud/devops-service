package io.choerodon.devops.domain.application.convertor;

import io.choerodon.core.convertor.ConvertorI;
import io.choerodon.devops.api.dto.DevopsEnviromentRepDTO;
import io.choerodon.devops.api.dto.DevopsEnviromentRepExDTO;
import io.choerodon.devops.domain.application.entity.DevopsEnvironmentE;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DevopsEnvironmentRepExConvertor implements ConvertorI<DevopsEnvironmentE, Object, DevopsEnviromentRepExDTO> {

    @Override
    public DevopsEnviromentRepExDTO entityToDto(DevopsEnvironmentE devopsEnvironmentE) {
        DevopsEnviromentRepExDTO devopsEnviromentRepDTO = new DevopsEnviromentRepExDTO();
        BeanUtils.copyProperties(devopsEnvironmentE, devopsEnviromentRepDTO);
        if(devopsEnvironmentE.getClusterE() != null) {
            devopsEnviromentRepDTO.setClusterId(devopsEnvironmentE.getClusterE().getId());
        }
        return devopsEnviromentRepDTO;
    }

}