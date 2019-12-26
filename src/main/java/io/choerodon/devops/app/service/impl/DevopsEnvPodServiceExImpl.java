package io.choerodon.devops.app.service.impl;

import io.choerodon.core.exception.CommonException;
import io.choerodon.devops.app.service.DevopsEnvPodServiceEx;
import io.choerodon.devops.domain.application.entity.ApplicationInstanceE;
import io.choerodon.devops.domain.application.entity.DevopsEnvironmentE;
import io.choerodon.devops.domain.application.repository.ApplicationInstanceRepository;
import io.choerodon.devops.domain.application.repository.DevopsEnvironmentRepository;
import io.choerodon.devops.infra.dataobject.DevopsEnvPodDO;
import io.choerodon.devops.infra.mapper.DevopsEnvPodMapper;
import io.choerodon.websocket.Msg;
import io.choerodon.websocket.helper.CommandSender;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DevopsEnvPodServiceExImpl implements DevopsEnvPodServiceEx {

    @Autowired
    private CommandSender commandSender;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private DevopsEnvPodMapper podMapper;

    @Autowired
    private DevopsEnvironmentRepository devopsEnvironmentRepository;

    @Autowired
    private ApplicationInstanceRepository applicationInstanceRepository;

    @Override
    public void removePod(Long projectId, Long envId, Long podId) {
        Msg msg = new Msg();
        Map<String, String> payload =  new HashMap<>();

        DevopsEnvPodDO devopsEnvPodDO = new DevopsEnvPodDO();
        devopsEnvPodDO.setProjectId(projectId);
        devopsEnvPodDO.setEnvId(envId);
        devopsEnvPodDO.setId(podId);

        DevopsEnvPodDO devopsEnvPodDOSelected = podMapper.selectOne(devopsEnvPodDO);

        if(devopsEnvPodDOSelected != null) {
            payload.put("PodName", devopsEnvPodDOSelected.getName());
            payload.put("Namespace", devopsEnvPodDOSelected.getNamespace());

            ApplicationInstanceE applicationInstanceE = applicationInstanceRepository.selectById(devopsEnvPodDOSelected.getAppInstanceId());
            DevopsEnvironmentE devopsEnvironmentE = devopsEnvironmentRepository
                    .queryById(envId);

            if(applicationInstanceE != null && devopsEnvironmentE != null){
                msg.setKey(String.format(    String.format("cluster:%d.env:%s.envId:%d.release:%s",
                        devopsEnvironmentE.getClusterE().getId(),
                        devopsEnvironmentE.getCode(),
                        devopsEnvironmentE.getId(),
                        applicationInstanceE.getCode())));
                msg.setType("kubernete_del_pod");
                try {
                    msg.setPayload(mapper.writeValueAsString(payload));
                } catch (IOException e) {
                    throw new CommonException("error.payload.error", e);
                }
                commandSender.sendMsg(msg);
            }
        }
    }
}
