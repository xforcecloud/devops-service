package io.choerodon.devops.infra.persistence.impl;

import io.choerodon.devops.domain.application.entity.DevopsEnvironmentE;
import io.choerodon.devops.domain.application.repository.DevopsEnvQuotaRepository;
import io.choerodon.devops.domain.application.repository.DevopsEnvironmentRepository;
import io.choerodon.devops.infra.dataobject.DevopsEnvQuotaDO;
import io.choerodon.devops.infra.mapper.DevopsEnvQuotaMapper;
import io.choerodon.websocket.tool.KeyParseTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DevopsEnvQuotaRepositoryImpl implements DevopsEnvQuotaRepository {

    @Autowired
    private DevopsEnvQuotaMapper mapper;

    /**
     * @param devopsEnvResourceDO
     * @return
     */
    @Override
    public int createOrUpdate(DevopsEnvQuotaDO devopsEnvResourceDO) {

        String name    = devopsEnvResourceDO.getName();
        Long envId     = devopsEnvResourceDO.getEnvId();
        Long clusterId = devopsEnvResourceDO.getClusterId();
        DevopsEnvQuotaDO quota = mapper.selectBy(name, clusterId, envId);
        if(quota != null){
            devopsEnvResourceDO.setId(quota.getId());
            devopsEnvResourceDO.setObjectVersionNumber(quota.getObjectVersionNumber());
            mapper.updateByPrimaryKeySelective(devopsEnvResourceDO);
        } else {
            mapper.insert(devopsEnvResourceDO);
        }

        return 1;
    }
}
