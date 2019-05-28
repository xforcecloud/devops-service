package io.choerodon.devops.infra.mapper;

import io.choerodon.devops.infra.dataobject.DevopsEnvQuotaDO;
import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DevopsEnvQuotaMapper extends BaseMapper<DevopsEnvQuotaDO> {

    DevopsEnvQuotaDO selectBy(@Param("name") String name
            , @Param("clusterId") Long clusterId
            , @Param("envId") Long envId);

    List<DevopsEnvQuotaDO> selectByEnvIds(@Param("envIds") List<Long> envIds);
}
