package io.choerodon.devops.app.service.impl;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.devops.api.dto.GitlabUserRequestDTO;
import io.choerodon.devops.app.service.GitlabUserService;
import io.choerodon.devops.domain.application.entity.UserAttrE;
import io.choerodon.devops.domain.application.entity.gitlab.GitlabUserE;
import io.choerodon.devops.domain.application.event.GitlabUserEvent;
import io.choerodon.devops.domain.application.repository.GitlabUserRepository;
import io.choerodon.devops.domain.application.repository.UserAttrRepository;
import io.choerodon.devops.infra.common.util.TypeUtil;
import io.choerodon.devops.infra.config.GitlabConfigurationProperties;
import io.choerodon.core.exception.CommonException;
import java.util.regex.Pattern;

/**
 * Created by Zenger on 2018/3/28.
 */
@Service
public class GitlabUserServiceImpl implements GitlabUserService {
    private static final String SERVICE_PATTERN = "[a-zA-Z0-9_\\.][a-zA-Z0-9_\\-\\.]*[a-zA-Z0-9_\\-]|[a-zA-Z0-9_]";

    @Autowired
    private GitlabConfigurationProperties gitlabConfigurationProperties;
    @Autowired
    private GitlabUserRepository gitlabUserRepository;
    @Autowired
    private UserAttrRepository userAttrRepository;

    private Pattern pattern = Pattern.compile("[\\w+|\\-|.|_]+");

    private String getValidateGitLabName(String loginName){
        String removeAtName = loginName.replaceAll("@", "_");
        if(!pattern.matcher(removeAtName).matches()
                ||  removeAtName.endsWith(".git")
                || removeAtName.endsWith(".")
                || removeAtName.endsWith(".atom")
                || removeAtName.startsWith("-")){
            throw new CommonException("error.user.name.illegal");
        }
        return removeAtName;
    }

    @Override
    public void createGitlabUser(GitlabUserRequestDTO gitlabUserReqDTO) {
        //transfer
        String validateUserName = getValidateGitLabName(gitlabUserReqDTO.getUsername());
        gitlabUserReqDTO.setUsername(validateUserName);

        checkGitlabUser(gitlabUserReqDTO);
        GitlabUserE createOrUpdateGitlabUserE = gitlabUserRepository.createGitLabUser(
                gitlabConfigurationProperties.getPassword(),
                gitlabConfigurationProperties.getProjectLimit(),
                ConvertHelper.convert(gitlabUserReqDTO, GitlabUserEvent.class));

        if (createOrUpdateGitlabUserE != null) {
            UserAttrE userAttrE = new UserAttrE();
            userAttrE.setIamUserId(Long.parseLong(gitlabUserReqDTO.getExternUid()));
            userAttrE.setGitlabUserId(createOrUpdateGitlabUserE.getId().longValue());
            userAttrE.setGitlabUserName(createOrUpdateGitlabUserE.getUsername());
            userAttrRepository.insert(userAttrE);
        }
    }

    @Override
    public void updateGitlabUser(GitlabUserRequestDTO gitlabUserReqDTO) {

        checkGitlabUser(gitlabUserReqDTO);
        UserAttrE userAttrE = userAttrRepository.queryById(TypeUtil.objToLong(gitlabUserReqDTO.getExternUid()));
        if (userAttrE != null) {

            gitlabUserRepository.updateGitLabUser(TypeUtil.objToInteger(userAttrE.getGitlabUserId()),
                    gitlabConfigurationProperties.getProjectLimit(),
                    ConvertHelper.convert(gitlabUserReqDTO, GitlabUserEvent.class));
        }
    }

    @Override
    public void isEnabledGitlabUser(Integer userId) {
        UserAttrE userAttrE = userAttrRepository.queryById(TypeUtil.objToLong(userId));
        if (userAttrE != null) {
            gitlabUserRepository.isEnabledGitlabUser(TypeUtil.objToInteger(userAttrE.getGitlabUserId()));
        }
    }

    @Override
    public void disEnabledGitlabUser(Integer userId) {
        UserAttrE userAttrE = userAttrRepository.queryById(TypeUtil.objToLong(userId));
        if (userAttrE != null) {
            gitlabUserRepository.disEnabledGitlabUser(TypeUtil.objToInteger(userAttrE.getGitlabUserId()));
        }
    }


    private void checkGitlabUser(GitlabUserRequestDTO gitlabUserRequestDTO) {
        String userName = gitlabUserRequestDTO.getUsername();
        String newUserName = "";
        for (int i = 0; i < userName.length(); i++) {
            if (!Pattern.matches(SERVICE_PATTERN, String.valueOf(userName.charAt(i)))) {
                newUserName += "_";
            } else {
                newUserName += String.valueOf(userName.charAt(i));
            }
        }
        gitlabUserRequestDTO.setUsername(newUserName);
    }
}
