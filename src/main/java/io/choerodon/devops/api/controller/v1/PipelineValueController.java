package io.choerodon.devops.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.choerodon.base.annotation.Permission;
import io.choerodon.base.enums.ResourceType;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.iam.InitRoleCode;
import io.choerodon.devops.api.dto.PipelineValueDTO;
import io.choerodon.devops.app.service.PipelineValueService;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.swagger.annotation.CustomPageRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Creator: ChangpingShi0213@gmail.com
 * Date:  9:56 2019/4/10
 * Description:
 */
@RestController
@RequestMapping(value = "/v1/projects/{project_id}/pipeline_value")
public class PipelineValueController {
    @Autowired
    private PipelineValueService pipelineValueService;

    /**
     * 项目下获取流水线配置
     *
     * @param projectId   项目Id
     * @param pageRequest 分页参数
     * @param params      查询参数
     * @return
     */
    @Permission(type= ResourceType.PROJECT,roles = {InitRoleCode.PROJECT_OWNER, InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "项目下获取流水线配置")
    @CustomPageRequest
    @PostMapping("/list_by_options")
    public ResponseEntity<Page<PipelineValueDTO>> listByOptions(
            @ApiParam(value = "项目Id", required = true)
            @PathVariable(value = "project_id") Long projectId,
            @ApiParam(value = "应用Id", required = false)
            @RequestParam(value = "app_id", required = false) Long appId,
            @ApiParam(value = "环境Id", required = false)
            @RequestParam(value = "env_id", required = false) Long envId,
            @ApiParam(value = "分页参数")
            @ApiIgnore PageRequest pageRequest,
            @ApiParam(value = "查询参数")
            @RequestBody(required = false) String params) {
        return Optional.ofNullable(pipelineValueService.listByOptions(projectId, appId, envId, pageRequest, params))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.pipeline.value.list"));
    }

    /**
     * 项目下创建流水线配置
     *
     * @param projectId        项目Id
     * @param pipelineValueDTO 配置信息
     * @return
     */
    @Permission(type= ResourceType.PROJECT,roles = {InitRoleCode.PROJECT_OWNER, InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "项目下创建流水线配置")
    @PostMapping
    public ResponseEntity<PipelineValueDTO> createOrUpdate(
            @ApiParam(value = "项目Id", required = true)
            @PathVariable(value = "project_id") Long projectId,
            @ApiParam(value = "PipelineValueDTO")
            @RequestBody PipelineValueDTO pipelineValueDTO) {
        return Optional.ofNullable(pipelineValueService.createOrUpdate(projectId, pipelineValueDTO))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.pipeline.value.createOrUpdate"));
    }

    /**
     * 项目下查询配置详情
     *
     * @param projectId 项目Id
     * @param valueId   配置Id
     * @return
     */
    @Permission(type= ResourceType.PROJECT,roles = {InitRoleCode.PROJECT_OWNER, InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "项目下查询配置详情")
    @GetMapping
    public ResponseEntity<PipelineValueDTO> queryById(
            @ApiParam(value = "项目Id", required = true)
            @PathVariable(value = "project_id") Long projectId,
            @ApiParam(value = "valueId", required = true)
            @RequestParam(value = "value_id") Long valueId) {
        return Optional.ofNullable(pipelineValueService.queryById(projectId, valueId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.pipeline.value.queryById"));
    }

    /**
     * 项目下删除配置
     *
     * @param projectId 项目Id
     * @param valueId   配置Id
     * @return
     */
    @Permission(type= ResourceType.PROJECT,roles = {InitRoleCode.PROJECT_OWNER, InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "项目下删除配置")
    @DeleteMapping
    public ResponseEntity delete(
            @ApiParam(value = "项目Id", required = true)
            @PathVariable(value = "project_id") Long projectId,
            @ApiParam(value = "valueId", required = true)
            @RequestParam(value = "value_id") Long valueId) {
        pipelineValueService.delete(projectId, valueId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    /**
     * 名称校验
     *
     * @param projectId
     * @param name
     * @return
     */
    @Permission(type= ResourceType.PROJECT,roles = {InitRoleCode.PROJECT_OWNER, InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "名称校验")
    @GetMapping("/check_name")
    public ResponseEntity checkName(
            @ApiParam(value = "项目Id", required = true)
            @PathVariable(value = "project_id") Long projectId,
            @ApiParam(value = "名称", required = true)
            @RequestParam(value = "name") String name) {
        pipelineValueService.checkName(projectId, name);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 检测能否删除
     *
     * @param projectId
     * @param valueId
     * @return
     */
    @Permission(type= ResourceType.PROJECT,roles = {InitRoleCode.PROJECT_OWNER, InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "能否删除")
    @GetMapping("/check_delete")
    public ResponseEntity<Boolean> checkDelete(
            @ApiParam(value = "项目Id", required = true)
            @PathVariable(value = "project_id") Long projectId,
            @ApiParam(value = "valueId", required = true)
            @RequestParam(value = "value_id") Long valueId) {
        return Optional.ofNullable(pipelineValueService.checkDelete(projectId, valueId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.pipeline.value.check.delete"));
    }

    /**
     * 根据应用Id和环境Id获取配置
     *
     * @param projectId
     * @param appId
     * @param envId
     * @return
     */
    @Permission(type= ResourceType.PROJECT,roles = {InitRoleCode.PROJECT_OWNER, InitRoleCode.PROJECT_MEMBER})
    @ApiOperation(value = "根据应用Id和环境Id获取配置")
    @GetMapping("/list")
    public ResponseEntity<List<PipelineValueDTO>> queryByAppIdAndEnvId(
            @ApiParam(value = "项目Id", required = true)
            @PathVariable(value = "project_id") Long projectId,
            @ApiParam(value = "应用Id", required = true)
            @RequestParam(value = "app_id") Long appId,
            @ApiParam(value = "环境Id", required = true)
            @RequestParam(value = "env_id") Long envId) {
        return Optional.ofNullable(pipelineValueService.queryByAppIdAndEnvId(projectId, appId, envId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.pipeline.value.queryByIds"));
    }
}