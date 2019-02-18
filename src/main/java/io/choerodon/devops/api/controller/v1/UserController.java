package io.choerodon.devops.api.controller.v1;

import io.choerodon.devops.api.dto.GitlabUserRequestDTO;
import io.choerodon.devops.app.service.GitlabUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 版权：    上海云砺信息科技有限公司
 * 创建者:   youyifan
 * 创建时间: 2/17/2019 10:52 PM
 * 功能描述:
 * 修改历史:
 */
@RestController
@RequestMapping("/v1/users/{userId}")
public class UserController {
    @Autowired
    GitlabUserService gitlabUserService;

    @PutMapping("/password")
    void updateUserPassword(@PathVariable("userId") Integer userId,
                            @RequestParam(value = "password") String password) {
        GitlabUserRequestDTO gitlabUserReqDTO = new GitlabUserRequestDTO();
        gitlabUserReqDTO.setExternUid(userId.toString());
        gitlabUserReqDTO.setPassword(password);

        gitlabUserService.updateGitlabUserPassword(gitlabUserReqDTO);
    }
}
