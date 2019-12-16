package io.choerodon.devops.infra.persistence.impl;

import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.devops.api.dto.DuckulaItem;
import io.choerodon.devops.api.dto.DuckulaRep;
import io.choerodon.devops.domain.application.entity.DevopsEnvironmentE;
import io.choerodon.devops.domain.application.repository.DevopsEnvironmentExRepository;
import io.choerodon.devops.infra.dataobject.DevopsDuckulaDO;
import io.choerodon.devops.infra.dataobject.DevopsEnvironmentDO;
import io.choerodon.devops.infra.dataobject.DevopsEnvironmentProps;
import io.choerodon.devops.infra.mapper.DevopsDuckulaMapper;
import io.choerodon.devops.infra.mapper.DevopsEnvironmentMapper;
import io.choerodon.devops.infra.mapper.DevopsEnvironmentPropsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by younger on 2018/4/9.
 */
@Service
public class DevopsEnvironmentExRepositoryImpl implements DevopsEnvironmentExRepository {

    private DevopsEnvironmentMapper devopsEnvironmentMapper;

    private DevopsDuckulaMapper devopsDuckulaMapper;

    private DevopsEnvironmentPropsMapper propsMapper;

    public DevopsEnvironmentExRepositoryImpl(DevopsEnvironmentMapper devopsEnvironmentMapper
            , DevopsDuckulaMapper devopsDuckulaMapper
            , DevopsEnvironmentPropsMapper propsMapper
    ) {
        this.devopsEnvironmentMapper = devopsEnvironmentMapper;
        this.devopsDuckulaMapper = devopsDuckulaMapper;
        this.propsMapper = propsMapper;
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
        duckulaDO.setObjectVersionNumber(devopsDuckulaMapper.selectByPrimaryKey(envId).getObjectVersionNumber());

        System.out.println("update:" + duckulaDO);
        return devopsDuckulaMapper.updateByPrimaryKeySelective(duckulaDO);
    }

    @Override
    public List<DuckulaItem> getItems(Long projectId, String cate) {
        DevopsEnvironmentProps props = new DevopsEnvironmentProps();
        props.setCate(cate);
        List<DevopsEnvironmentProps> result = propsMapper.select(props);
        return result.stream().map(x -> {
            DuckulaItem item = new DuckulaItem();
            item.setCode(x.getCode());
            item.setName(x.getName());
            item.setUrl(x.getValue());
            return item;
        }).collect(Collectors.toList());
    }
}
