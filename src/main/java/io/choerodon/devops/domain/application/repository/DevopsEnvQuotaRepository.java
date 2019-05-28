package io.choerodon.devops.domain.application.repository;

import io.choerodon.devops.infra.dataobject.DevopsEnvQuotaDO;

import java.util.List;

public interface DevopsEnvQuotaRepository {

    //insert or update
    int createOrUpdate(DevopsEnvQuotaDO devopsEnvFileErrorE);

    List<DevopsEnvQuotaDO> findByEnvIds(List<Long> envIds);

}
