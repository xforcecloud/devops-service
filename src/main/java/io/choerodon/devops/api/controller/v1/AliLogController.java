package io.choerodon.devops.api.controller.v1;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.iam.InitRoleCode;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.devops.api.dto.DevopsClusterRepDTO;
import io.choerodon.devops.app.service.AliLogService;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 版权：    上海云砺信息科技有限公司
 * 创建者:   youyifan
 * 创建时间: 4/9/2019 5:55 PM
 * 功能描述:
 * 修改历史:
 */

@RestController
@RequestMapping(value = "/v1/organizations/{organization_id}/alilog")
public class AliLogController {
    @Autowired
    AliLogService aliLogService;

    @Permission(level = ResourceLevel.ORGANIZATION, roles = {InitRoleCode.ORGANIZATION_ADMINISTRATOR})
    @ApiOperation(value = "获取阿里云日志URL")
    @GetMapping("/logsearch")
    public ResponseEntity<String> createSignInUrl() {
        return Optional.ofNullable(aliLogService.createSignInUrl())
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.alilog.createurl"));
    }
}
