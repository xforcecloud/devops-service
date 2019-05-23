package io.choerodon.devops.app.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.core.exception.CommonException;
import io.choerodon.devops.api.dto.GitConfigDTO;
import io.choerodon.devops.app.service.DeployMsgHandlerService;
import io.choerodon.devops.app.service.DeployMsgHandlerServiceEx;
import io.choerodon.devops.domain.application.entity.*;
import io.choerodon.devops.domain.application.factory.DevopsInstanceResourceFactory;
import io.choerodon.devops.domain.application.repository.*;
import io.choerodon.devops.domain.application.valueobject.*;
import io.choerodon.devops.infra.common.util.*;
import io.choerodon.devops.infra.common.util.enums.*;
import io.choerodon.devops.infra.config.HarborConfigurationProperties;
import io.choerodon.devops.infra.dataobject.DevopsEnvPodContainerDO;
import io.choerodon.devops.infra.dataobject.DevopsEnvQuotaDO;
import io.choerodon.devops.infra.dataobject.DevopsIngressDO;
import io.choerodon.devops.infra.mapper.ApplicationMarketMapper;
import io.choerodon.devops.infra.mapper.DevopsIngressMapper;
import io.choerodon.websocket.Msg;
import io.choerodon.websocket.process.SocketMsgDispatcher;
import io.choerodon.websocket.tool.KeyParseTool;
import io.kubernetes.client.JSON;
import io.kubernetes.client.models.V1OwnerReference;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1Service;
import io.kubernetes.client.models.V1beta1Ingress;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Zenger on 2018/4/17.
 */
@Service
public class DeployMsgHandlerServiceExImpl implements DeployMsgHandlerServiceEx {

    @Autowired
    private DevopsEnvQuotaRepository devopsEnvQuotaRepository;

    @Autowired
    private DevopsEnvironmentRepository devopsEnvironmentRepository;

    @Override
    public void quotaUpdate(String key, String msg, Long clusterId) {
        //TO devopsEnvQuotaDO
        Long envId = getEnvId(key, clusterId);

        DevopsEnvQuotaDO envQuotaDO = new DevopsEnvQuotaDO();
        envQuotaDO.setClusterId(clusterId);
        envQuotaDO.setPayload(msg);
        envQuotaDO.setEnvId(envId);

        devopsEnvQuotaRepository.createOrUpdate(envQuotaDO);
    }

    @Override
    public void quotaRemoved(String key, String msg, Long clusterId) {
        System.out.println(key);
        System.out.println(msg);
        System.out.println(clusterId);
    }

    private Long getEnvId(String key, Long clusterId) {
        DevopsEnvironmentE devopsEnvironmentE = devopsEnvironmentRepository.queryByClusterIdAndCode(clusterId, KeyParseTool.getNamespace(key));
        Long envId = null;
        if (devopsEnvironmentE != null) {
            envId = devopsEnvironmentE.getId();
        }
        return envId;
    }
}

