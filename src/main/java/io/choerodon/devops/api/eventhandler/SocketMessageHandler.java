package io.choerodon.devops.api.eventhandler;

import io.choerodon.devops.app.service.DeployMsgHandlerServiceEx;
import io.choerodon.devops.domain.application.entity.DevopsEnvironmentE;
import io.choerodon.devops.domain.application.repository.DevopsEnvironmentRepository;
import io.choerodon.devops.infra.feign.XDevopsClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.choerodon.devops.app.service.DeployMsgHandlerService;
import io.choerodon.devops.infra.common.util.TypeUtil;
import io.choerodon.devops.infra.common.util.enums.CommandStatus;
import io.choerodon.devops.infra.common.util.enums.HelmType;
import io.choerodon.devops.infra.common.util.enums.InstanceStatus;
import io.choerodon.websocket.Msg;
import io.choerodon.websocket.process.AbstractAgentMsgHandler;
import io.choerodon.websocket.tool.KeyParseTool;

/**
 * Created by Zenger on 2018/4/17.
 */
@Component
public class SocketMessageHandler extends AbstractAgentMsgHandler {

    private static final Logger logger = LoggerFactory.getLogger(SocketMessageHandler.class);

    private DeployMsgHandlerService deployMsgHandlerService;

    private DeployMsgHandlerServiceEx deployMsgHandlerServiceEx;

    private XDevopsClient client;

    private DevopsEnvironmentRepository devopsEnvironmentRepository;

    @Value("${trace:false}")
    private Boolean useTrace = false;

    @Autowired
    public SocketMessageHandler(DeployMsgHandlerService deployMsgHandlerService, DeployMsgHandlerServiceEx deployMsgHandlerServiceEx, XDevopsClient client, DevopsEnvironmentRepository devopsEnvironmentRepository) {
        this.deployMsgHandlerService = deployMsgHandlerService;
        this.deployMsgHandlerServiceEx = deployMsgHandlerServiceEx;
        this.client = client;
        this.devopsEnvironmentRepository = devopsEnvironmentRepository;
    }


    private Long getEnvId(String key, Long clusterId) {
        DevopsEnvironmentE devopsEnvironmentE = devopsEnvironmentRepository.queryByClusterIdAndCode(clusterId, KeyParseTool.getNamespace(key));
        Long envId = null;
        if (devopsEnvironmentE != null) {
            envId = devopsEnvironmentE.getId();
        }
        return envId;
    }

    @Override
    public void process(Msg msg) {
        HelmType helmType = HelmType.forString(String.valueOf(msg.getType()));
        if (helmType == null) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug(msg.toString());
        }
        try {
            if(useTrace) {
                String payload = msg.getPayload();
                if (StringUtils.isEmpty(payload)) {
                    payload = "{}";
                }

                Long envId = getEnvId(msg.getKey(), TypeUtil.objToLong(msg.getClusterId()));
                if (envId != null) {
                    //TODO async
                    client.recordSocketMsg(msg.getKey(), helmType.value, envId, payload);
                }
            }
        }catch(RuntimeException ex){
            ex.printStackTrace();
        }

        switch (helmType) {
            case HELM_RELEASE_PRE_INSTALL:
                deployMsgHandlerService.handlerPreInstall(
                        msg.getKey(),
                        msg.getPayload(),
                        TypeUtil.objToLong(msg.getClusterId()));
                break;
            case HELM_INSTALL_RELEASE:
                deployMsgHandlerService.handlerReleaseInstall(msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case HELM_RELEASE_UPGRADE:
                deployMsgHandlerService.handlerReleaseUpgrade(msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                deployMsgHandlerService.updateInstanceStatus(
                        msg.getKey(),
                        KeyParseTool.getResourceName(msg.getKey()),
                        TypeUtil.objToLong(msg.getClusterId()),
                        InstanceStatus.RUNNING.getStatus(),
                        CommandStatus.SUCCESS.getStatus(),
                        "");
                break;
            case HELM_RELEASE_ROLLBACK:
                break;
            case HELM_RELEASE_START:
                deployMsgHandlerService.updateInstanceStatus(
                        msg.getKey(),
                        KeyParseTool.getResourceName(msg.getKey()),
                        TypeUtil.objToLong(msg.getClusterId()),
                        InstanceStatus.RUNNING.getStatus(),
                        CommandStatus.SUCCESS.getStatus(),
                        "");
                break;
            case HELM_RELEASE_STOP:
                deployMsgHandlerService.updateInstanceStatus(
                        msg.getKey(),
                        KeyParseTool.getResourceName(msg.getKey()),
                        TypeUtil.objToLong(msg.getClusterId()),
                        InstanceStatus.STOPPED.getStatus(),
                        CommandStatus.SUCCESS.getStatus(),
                        "");
                break;
            case HELM_RELEASE_DELETE:
                deployMsgHandlerService.helmReleaseDelete(KeyParseTool.getResourceName(msg.getKey()), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case HELM_RELEASE_PRE_UPGRADE:
                deployMsgHandlerService.helmReleasePreUpgrade(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case NETWORK_SERVICE:
                deployMsgHandlerService.handlerServiceCreateMessage(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case NETWORK_INGRESS:
                deployMsgHandlerService.handlerDomainCreateMessage(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case NETWORK_INGRESS_DELETE:
                break;
            case RESOURCE_UPDATE:
                deployMsgHandlerService.resourceUpdate(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case RESOURCE_DELETE:
                deployMsgHandlerService.resourceDelete(msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case HELM_RELEASE_HOOK_LOGS:
                deployMsgHandlerService.helmReleaseHookLogs(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case NETWORK_SERVICE_UPDATE:
                deployMsgHandlerService.netWorkUpdate(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case HELM_RELEASE_DELETE_FAILED:
                deployMsgHandlerService.helmReleaseDeleteFail(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case HELM_RELEASE_INSTALL_FAILED:
                deployMsgHandlerService.helmReleaseInstallFail(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case HELM_RELEASE_START_FAILED:
                deployMsgHandlerService.helmReleaseStartFail(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case HELM_RELEASE_STOP_FAILED:
                deployMsgHandlerService.helmReleaeStopFail(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case HELM_RELEASE_ROLLBACK_FAILED:
                deployMsgHandlerService.helmReleaseRollBackFail(msg.getKey(), msg.getPayload());
                break;
            case HELM_RELEASE_UPGRADE_FAILED:
                deployMsgHandlerService.helmReleaseUpgradeFail(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case HELM_RELEASE_GET_CONTENT:
                deployMsgHandlerService.helmReleaseGetContent(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case COMMAND_NOT_SEND:
                deployMsgHandlerService.commandNotSend(msg.getCommandId(), msg.getPayload());
                break;
            case NETWORK_SERVICE_FAILED:
                deployMsgHandlerService.netWorkServiceFail(msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case NETWORK_INGRESS_FAILED:
                deployMsgHandlerService.netWorkIngressFail(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case NETWORK_SERVICE_DELETE_FAILED:
                deployMsgHandlerService.netWorkServiceDeleteFail(msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case NETWORK_INGRESS_DELETE_FAILED:
                deployMsgHandlerService.netWorkIngressDeleteFail(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case RESOURCE_SYNC:
                deployMsgHandlerService.resourceSync(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case JOB_EVENT:
                deployMsgHandlerService.jobEvent(msg.getPayload());
                break;
            case RELEASE_POD_EVENT:
                deployMsgHandlerService.releasePodEvent(
                        msg.getPayload());
                break;
            case GIT_OPS_SYNC_EVENT:
                deployMsgHandlerService.gitOpsSyncEvent(msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case STATUS_SYNC_EVENT:
                deployMsgHandlerService.gitOpsCommandSyncEvent(msg.getKey(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case CERT_ISSUED:
                deployMsgHandlerService.certIssued(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case CERT_FAILED:
                deployMsgHandlerService.certFailed(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case STATUS_SYNC:
                deployMsgHandlerService.gitOpsCommandSyncEventResult(msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case NAMESPACE_UPDATE:
                deployMsgHandlerService.updateNamespaces(msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case UPGRADE_CLUSTER:
                deployMsgHandlerService.upgradeCluster(msg.getKey(), msg.getPayload());
                break;
            case RES_QUOTA_UPDATE:
                deployMsgHandlerServiceEx.quotaUpdate(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            case RES_QUOTA_REMOVED:
                deployMsgHandlerServiceEx.quotaRemoved(
                        msg.getKey(), msg.getPayload(), TypeUtil.objToLong(msg.getClusterId()));
                break;
            default:
                msg.setDispatch(false);
                break;
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }

}
