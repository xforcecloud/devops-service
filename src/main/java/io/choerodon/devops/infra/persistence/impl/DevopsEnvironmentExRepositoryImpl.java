package io.choerodon.devops.infra.persistence.impl;

import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.devops.api.dto.DuckulaRep;
import io.choerodon.devops.domain.application.entity.DevopsEnvironmentE;
import io.choerodon.devops.domain.application.repository.DevopsEnvironmentExRepository;
import io.choerodon.devops.infra.dataobject.DevopsDuckulaDO;
import io.choerodon.devops.infra.dataobject.DevopsEnvironmentDO;
import io.choerodon.devops.infra.mapper.DevopsDuckulaMapper;
import io.choerodon.devops.infra.mapper.DevopsEnvironmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by younger on 2018/4/9.
 */
@Service
public class DevopsEnvironmentExRepositoryImpl implements DevopsEnvironmentExRepository {

    private DevopsEnvironmentMapper devopsEnvironmentMapper;

    private DevopsDuckulaMapper devopsDuckulaMapper;

    public DevopsEnvironmentExRepositoryImpl(DevopsEnvironmentMapper devopsEnvironmentMapper, DevopsDuckulaMapper devopsDuckulaMapper ) {
        this.devopsEnvironmentMapper = devopsEnvironmentMapper;
        this.devopsDuckulaMapper = devopsDuckulaMapper;
    }

    @Override
    public List<DevopsEnvironmentE> queryByprojectAndActive(Long projectId, Boolean active) {
        DevopsEnvironmentDO devopsEnvironmentDO = new DevopsEnvironmentDO();
        devopsEnvironmentDO.setProjectId(projectId);
        devopsEnvironmentDO.setActive(active);
        List<DevopsEnvironmentDO> devopsEnvironmentDOS = devopsEnvironmentMapper.select(devopsEnvironmentDO);
        return ConvertHelper.convertList(devopsEnvironmentDOS, DevopsEnvironmentE.class);
    }

    @Override
    public DuckulaRep queryDuckula(Long projectId, Long envId) {
        DevopsDuckulaDO duckulaDO = new DevopsDuckulaDO();
        DevopsDuckulaDO rep = devopsDuckulaMapper.selectOne(duckulaDO);
        DuckulaRep duckulaRep = new DuckulaRep();
        if(rep != null){
            duckulaRep.setBaseUrl(rep.getUrl());
            return duckulaRep;
        }

        return duckulaRep;
    }

    @Override
    public int insertEnvDuckula(Long projectId, Long envId, String url) {
        DevopsDuckulaDO duckulaDO = new DevopsDuckulaDO();
        return devopsDuckulaMapper.insert(duckulaDO);
    }
}
