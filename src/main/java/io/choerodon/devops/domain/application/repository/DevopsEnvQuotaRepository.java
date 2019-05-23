package io.choerodon.devops.domain.application.repository;

import io.choerodon.devops.infra.dataobject.DevopsEnvQuotaDO;

public interface DevopsEnvQuotaRepository {

    //insert or update
    int createOrUpdate(DevopsEnvQuotaDO devopsEnvFileErrorE);

}
