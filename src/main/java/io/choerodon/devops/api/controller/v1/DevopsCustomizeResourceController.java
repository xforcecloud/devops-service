package io.choerodon.devops.api.controller.v1;

import java.util.Optional;

import com.github.pagehelper.PageInfo;
import io.choerodon.base.annotation.Permission;
import io.choerodon.base.domain.PageRequest;
import io.choerodon.base.domain.Sort;
import io.choerodon.base.enums.ResourceType;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.iam.InitRoleCode;
import io.choerodon.devops.api.dto.DevopsCustomizeResourceDTO;
import io.choerodon.devops.api.dto.DevopsCustomizeResourceReqDTO;
import io.choerodon.devops.app.service.DevopsCustomizeResourceService;
import io.choerodon.mybatis.annotation.SortDefault;
import io.choerodon.swagger.annotation.CustomPageRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by Sheep on 2019/6/26.
 */
@RestController
@RequestMapping(value = "/v1/projects/{project_id}/customize_resource")
public class DevopsCustomizeResourceController {

    @Autowired
    DevopsCustomizeResourceService devopsCustomizeResourceService;

    /**
     * 创建其他k8s资源
     *
     * @param projectId
     * @param contentFile
     * @return
     */
    @Permission(type = ResourceType.PROJECT, roles = {InitRoleCode.PROJECT_OWNER,
            InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "创建其他k8s资源")
    @PostMapping
    public ResponseEntity createResource(@PathVariable(value = "project_id") Long projectId,
                                         @ModelAttribute DevopsCustomizeResourceReqDTO devopsCustomizeResourceReqDTO,
                                         @RequestParam(value = "contentFile", required = false) MultipartFile contentFile) {
        devopsCustomizeResourceService.createOrUpdateResource(projectId, devopsCustomizeResourceReqDTO, contentFile);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 删除其他k8s资源
     *
     * @param projectId
     * @param resourceId
     * @return
     */
    @Permission(type = ResourceType.PROJECT, roles = {InitRoleCode.PROJECT_OWNER,
            InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "删除其他k8s资源")
    @DeleteMapping
    public ResponseEntity deleteResource(@PathVariable(value = "project_id") Long projectId,
                                         @RequestParam(value = "resource_id") Long resourceId) {
        devopsCustomizeResourceService.deleteResource(resourceId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 获取资源详情
     *
     * @param projectId
     * @param resourceId
     * @return
     */

    @Permission(type = ResourceType.PROJECT, roles = {InitRoleCode.PROJECT_OWNER,
            InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "获取资源详情")
    @GetMapping
    public ResponseEntity<DevopsCustomizeResourceDTO> getResource(@PathVariable(value = "project_id") Long projectId,
                                                                  @RequestParam(value = "resource_id") Long resourceId) {
        devopsCustomizeResourceService.getDevopsCustomizeResourceDetail(resourceId);
        return Optional.ofNullable(devopsCustomizeResourceService.getDevopsCustomizeResourceDetail(resourceId))
                .map(t -> new ResponseEntity<>(t, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.resource.get"));
    }


    /**
     * 其他K8S资源分页查询
     *
     * @param projectId   项目id
     * @param envId       环境id
     * @param pageRequest 分页参数
     * @param searchParam 查询参数
     * @return Page of DevopsCustomizeResourceDTO
     */
    @Permission(type = ResourceType.PROJECT,
            roles = {InitRoleCode.PROJECT_OWNER,
                    InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "其他K8S资源分页查询")
    @CustomPageRequest
    @PostMapping(value = "/{envId}/pageByEnv")
    public ResponseEntity<PageInfo<DevopsCustomizeResourceDTO>> pageByEnv(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable(value = "project_id") Long projectId,
            @ApiParam(value = "环境id", required = true)
            @PathVariable(value = "envId") Long envId,
            @ApiParam(value = "分页参数")
            @SortDefault(value = "id", direction = Sort.Direction.DESC)
            @ApiIgnore PageRequest pageRequest,
            @ApiParam(value = "查询参数")
            @RequestBody(required = false) String searchParam) {
        return Optional.ofNullable(devopsCustomizeResourceService.pageResources(envId, pageRequest, searchParam))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.resource.query"));
    }
}