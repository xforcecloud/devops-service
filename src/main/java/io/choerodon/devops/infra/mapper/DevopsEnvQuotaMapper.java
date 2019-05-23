package io.choerodon.devops.infra.mapper;

import io.choerodon.devops.infra.dataobject.DevopsEnvQuotaDO;
import io.choerodon.mybatis.common.BaseMapper;

public interface DevopsEnvQuotaMapper extends BaseMapper<DevopsEnvQuotaDO> {

    DevopsEnvQuotaDO selectBy(String name, Long clusterId, Long envId);
}
