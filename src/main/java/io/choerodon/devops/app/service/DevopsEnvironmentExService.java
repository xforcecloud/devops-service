package io.choerodon.devops.app.service;

import io.choerodon.devops.api.dto.DevopsEnviromentRepExDTO;

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

}
