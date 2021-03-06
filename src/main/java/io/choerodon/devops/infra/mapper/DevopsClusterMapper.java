package io.choerodon.devops.infra.mapper;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Param;

import io.choerodon.devops.infra.dto.DevopsClusterDTO;
import io.choerodon.devops.infra.dto.DevopsEnvPodDTO;
import io.choerodon.mybatis.common.Mapper;


public interface DevopsClusterMapper extends Mapper<DevopsClusterDTO> {

    List<DevopsClusterDTO> listByProjectId(@Param("projectId") Long projectId, @Param("organizationId") Long organizationId);

    void updateSkipCheckPro(@Param("clusterId") Long clusterId, @Param("skipCheckPro") Boolean skipCheckPro);

    List<DevopsClusterDTO> listClusters(@Param("projectId") Long projectId,
                                        @Param("searchParam") Map<String, Object> searchParam,
                                        @Param("params") List<String> params);

    /**
     * 查询节点下的Pod
     *
     * @param clusterId   集群id
     * @param nodeName    节点名称
     * @param searchParam 查询参数
     * @return pods
     */
    List<DevopsEnvPodDTO> pageQueryPodsByNodeName(@Param("clusterId") Long clusterId,
                                                  @Param("nodeName") String nodeName,
                                                  @Param("searchParam") Map<String, Object> searchParam,
                                                  @Param("params") List<String> params);

    void updateProjectId(@Param("orgId") Long orgId,
                         @Param("proId") Long proId);


    List<DevopsClusterDTO> listAllClustersToMigrate();

    DevopsClusterDTO queryClusterForUpdate(@Param("clusterId") Long clusterId);

    Long queryClusterIdBySystemEnvId(@Param("systemEnvId") Long systemEnvId);

    List<DevopsClusterDTO> listByOrganizationId(@Param("organizationId") Long organizationId);

    /**
     * 根据条件统计集群的个数
     *
     * @param organizationId 组织id，可为空
     * @param projectId      项目id，可为空
     * @return 个数
     */
    int countByOptions(@Nullable @Param("organizationId") Long organizationId,
                       @Nullable @Param("projectId") Long projectId);
}
