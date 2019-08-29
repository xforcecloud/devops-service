package io.choerodon.devops.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.choerodon.devops.api.dto.DevopsEnvPodContainerLogDTO;
import io.choerodon.devops.app.service.DevopsEnvPodServiceEx;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.iam.InitRoleCode;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.devops.api.dto.DevopsEnvPodDTO;
import io.choerodon.devops.app.service.DevopsEnvPodService;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.swagger.annotation.CustomPageRequest;
import io.choerodon.swagger.annotation.Permission;

/**
 * Created by Zenger on 2018/4/17.
 */
@RestController
@RequestMapping(value = "/v1/projects/{project_id}/app_pod")
public class DevopsEnvPodController {

    private DevopsEnvPodService devopsEnvPodService;

    private DevopsEnvPodServiceEx devopsEnvPodServiceEx;

    public DevopsEnvPodController(DevopsEnvPodService devopsEnvPodService, DevopsEnvPodServiceEx devopsEnvPodServiceEx) {
        this.devopsEnvPodService = devopsEnvPodService;
        this.devopsEnvPodServiceEx = devopsEnvPodServiceEx;
    }

    /**
     * 分页查询容器管理
     *
     * @param projectId   项目id
     * @param pageRequest 分页参数
     * @param searchParam 查询参数
     * @return page of devopsEnvPodDTO
     */
    @Permission(level = ResourceLevel.PROJECT,
            roles = {InitRoleCode.PROJECT_OWNER,
                    InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "分页查询容器管理")
    @CustomPageRequest
    @PostMapping(value = "/list_by_options")
    public ResponseEntity<Page<DevopsEnvPodDTO>> pageByOptions(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable(value = "project_id") Long projectId,
            @ApiParam(value = "分页参数")
            @ApiIgnore PageRequest pageRequest,
            @ApiParam(value = "环境id")
            @RequestParam(required = false) Long envId,
            @ApiParam(value = "应用id")
            @RequestParam(required = false) Long appId,
            @ApiParam(value = "查询参数")
            @RequestBody(required = false) String searchParam) {
        return Optional.ofNullable(devopsEnvPodService.listAppPod(
                projectId, envId, appId, pageRequest, searchParam))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.application.pod.query"));
    }


    /**
     * 操作 shell By Pod
     *
     * @param projectId 项目ID
     * @param podId     pod ID
     * @return List of DevopsEnvPodContainerLogDTO
     */
    @Permission(level = ResourceLevel.PROJECT,
            roles = {InitRoleCode.PROJECT_OWNER,
                    InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "获取日志shell信息 By Pod")
    @GetMapping(value = "/drop")
    public ResponseEntity<String> deletePod(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable(value = "project_id") Long projectId,
            @ApiParam(value = "环境id")
            @RequestParam(required = false) Long envId,
            @ApiParam(value = "pod ID", required = true)
            @RequestParam Long podId) {
        devopsEnvPodServiceEx.removePod(projectId, envId, podId);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
