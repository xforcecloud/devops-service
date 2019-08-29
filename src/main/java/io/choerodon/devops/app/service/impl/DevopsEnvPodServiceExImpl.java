package io.choerodon.devops.app.service.impl;

import io.choerodon.core.exception.CommonException;
import io.choerodon.devops.app.service.DevopsEnvPodServiceEx;
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

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private DevopsEnvPodMapper podMapper;

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
            msg.setKey(String.format("pod %s", devopsEnvPodDOSelected.getName()));
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
