package io.choerodon.devops.app.service;

import io.choerodon.devops.api.dto.DevopsEnviromentRepExDTO;
import io.choerodon.devops.api.dto.DuckulaItem;
import io.choerodon.devops.api.dto.DuckulaRep;
import io.swagger.models.Response;

import java.util.List;

/**
 * Created by younger on 2018/4/9.
 */
public interface DevopsEnvironmentExService {

    /**
     * 项目下查询环境
     *
     * @param projectId 项目id
     * @param active    是否可用
     * @return List
     */
    List<DevopsEnviromentRepExDTO> listByProjectIdAndActive(Long projectId, Boolean active);

    DuckulaRep findDuckula(Long projectId, Long envId);

    int setDuckula(Long projectId, Long envId, String duckulaUrl);

    Response saveDuckula(Long projectId, Long envId, DuckulaRep duckulaRep);

    List<DuckulaItem> getDuckula(Long projectId);
}
