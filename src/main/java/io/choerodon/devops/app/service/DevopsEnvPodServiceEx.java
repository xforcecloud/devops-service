package io.choerodon.devops.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.devops.api.dto.DevopsEnvPodDTO;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

/**
 *
 */
public interface DevopsEnvPodServiceEx {


    void removePod(Long projectId, Long envId, Long podId);
}
