package io.choerodon.devops.domain.application.repository;

import io.choerodon.devops.api.dto.DuckulaItem;
import io.choerodon.devops.api.dto.DuckulaRep;
import io.choerodon.devops.domain.application.entity.DevopsEnvironmentE;

import java.util.List;

/**
 * Created by younger on 2018/4/9.
 */
public interface DevopsEnvironmentExRepository {

    List<DevopsEnvironmentE> queryByprojectAndActive(Long projectId, Boolean active);

    DuckulaRep queryDuckula(Long projectId, Long envId);

    int insertEnvDuckula(Long projectId, Long envId, String url);

    int updateDuckula(Long projectId, Long envId, DuckulaRep duckulaRep);

    List<DuckulaItem> getItems(Long projectId, String cate);
}
