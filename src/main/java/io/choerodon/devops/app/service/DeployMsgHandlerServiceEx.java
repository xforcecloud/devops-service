package io.choerodon.devops.app.service;

import io.choerodon.devops.domain.application.entity.ApplicationE;

/**
 * Created by Zenger on 2018/4/17.
 */
public interface DeployMsgHandlerServiceEx {

    void quotaUpdate(String key, String msg, Long clusterId);

    void quotaRemoved(String key, String msg, Long clusterId);
}
