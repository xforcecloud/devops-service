package io.choerodon.devops.api.controller.v1;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.iam.InitRoleCode;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.devops.api.dto.DevopsEnviromentRepDTO;
import io.choerodon.devops.api.dto.DevopsEnviromentRepExDTO;
import io.choerodon.devops.app.service.DevopsEnvironmentExService;
import io.choerodon.devops.app.service.DevopsEnvironmentService;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by younger on 2018/4/9.
 */
@RestController
@RequestMapping(value = "/v1/projects/{project_id}/envs-ex")
public class DevopsEnvironmentExController {

    private DevopsEnvironmentExService devopsEnvironmentService;

    public DevopsEnvironmentExController(DevopsEnvironmentExService devopsEnvironmentService) {
        this.devopsEnvironmentService = devopsEnvironmentService;
    }

    /**
     * 项目下查询环境
     *
     * @param projectId 项目id
     * @param active    是否启用
     * @return List
     */
//    @Permission(level = ResourceLevel.PROJECT,
//            roles = {InitRoleCode.PROJECT_OWNER,
//                    InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "项目下查询环境")
    @GetMapping
    public ResponseEntity<List<DevopsEnviromentRepExDTO>> listByProjectIdAndActive(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Long projectId,
            @ApiParam(value = "是否启用", required = true)
            @RequestParam(value = "active") Boolean active) {
        return Optional.ofNullable(devopsEnvironmentService.listByProjectIdAndActive(projectId, active))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.environment.get"));
    }
}
