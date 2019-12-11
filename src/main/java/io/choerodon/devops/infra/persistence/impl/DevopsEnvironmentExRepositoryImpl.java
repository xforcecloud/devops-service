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
        duckulaDO.setProjectId(projectId);
        duckulaDO.setEnvId(envId);
        DevopsDuckulaDO rep = devopsDuckulaMapper.selectOne(duckulaDO);

        DuckulaRep duckulaRep = new DuckulaRep();
        duckulaRep.setCode(0);
        if(rep != null){
            duckulaRep.setCode(1);
            duckulaRep.setBaseUrl(rep.getUrl());
            return duckulaRep;
        }

        return duckulaRep;
    }

    @Override
    public int insertEnvDuckula(Long projectId, Long envId, String url) {
        DevopsDuckulaDO duckulaDO = new DevopsDuckulaDO();
        duckulaDO.setProjectId(projectId);
        duckulaDO.setEnvId(envId);
        duckulaDO.setUrl(url);
        System.out.println("insert:" + duckulaDO);
        return devopsDuckulaMapper.insert(duckulaDO);
    }

    @Override
    public int updateDuckula(Long projectId, Long envId, DuckulaRep duckulaRep) {
        DevopsDuckulaDO duckulaDO = new DevopsDuckulaDO();
        duckulaDO.setProjectId(projectId);
        duckulaDO.setEnvId(envId);
        duckulaDO.setUrl(duckulaRep.getBaseUrl());

        System.out.println("update:" + duckulaDO);
        return devopsDuckulaMapper.updateByPrimaryKeySelective(duckulaDO);
    }
}
