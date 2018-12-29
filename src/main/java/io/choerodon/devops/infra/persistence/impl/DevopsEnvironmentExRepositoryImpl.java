package io.choerodon.devops.infra.persistence.impl;

import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.devops.domain.application.entity.DevopsEnvironmentE;
import io.choerodon.devops.domain.application.repository.DevopsEnvironmentExRepository;
import io.choerodon.devops.infra.dataobject.DevopsEnvironmentDO;
import io.choerodon.devops.infra.mapper.DevopsEnvironmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by younger on 2018/4/9.
 */
@Service
public class DevopsEnvironmentExRepositoryImpl implements DevopsEnvironmentExRepository {

    private DevopsEnvironmentMapper devopsEnvironmentMapper;

    public DevopsEnvironmentExRepositoryImpl(DevopsEnvironmentMapper devopsEnvironmentMapper) {
        this.devopsEnvironmentMapper = devopsEnvironmentMapper;
    }

    @Override
    public List<DevopsEnvironmentE> queryByprojectAndActive(Long projectId, Boolean active) {
        DevopsEnvironmentDO devopsEnvironmentDO = new DevopsEnvironmentDO();
        devopsEnvironmentDO.setProjectId(projectId);
        devopsEnvironmentDO.setActive(active);
        List<DevopsEnvironmentDO> devopsEnvironmentDOS = devopsEnvironmentMapper.select(devopsEnvironmentDO);
        return ConvertHelper.convertList(devopsEnvironmentDOS, DevopsEnvironmentE.class);
    }
}
