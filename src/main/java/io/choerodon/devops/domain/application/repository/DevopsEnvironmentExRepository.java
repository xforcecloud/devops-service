package io.choerodon.devops.domain.application.repository;

import io.choerodon.devops.domain.application.entity.DevopsEnvironmentE;

import java.util.List;

/**
 * Created by younger on 2018/4/9.
 */
public interface DevopsEnvironmentExRepository {

    List<DevopsEnvironmentE> queryByprojectAndActive(Long projectId, Boolean active);

}
