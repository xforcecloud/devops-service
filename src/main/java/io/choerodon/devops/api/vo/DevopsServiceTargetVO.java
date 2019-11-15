package io.choerodon.devops.api.vo;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

/**
 * Creator: Runge
 * Date: 2018/8/3
 * Time: 13:39
 * Description:
 */
public class DevopsServiceTargetVO {
    private List<AppServiceInstanceInfoVO> instances;
    /**
     * 是创建网络时所填的标签，也是这个网络本身的选择器
     */
    @ApiModelProperty("网络的选择器")
    private Map<String, String> selectors;
    private Map<String, List<EndPointPortVO>> endPoints;

    public List<AppServiceInstanceInfoVO> getInstances() {
        return instances;
    }

    public void setInstances(List<AppServiceInstanceInfoVO> instances) {
        this.instances = instances;

    }

    public Map<String, List<EndPointPortVO>> getEndPoints() {
        return endPoints;
    }

    public Map<String, String> getSelectors() {
        return selectors;
    }

    public void setSelectors(Map<String, String> selectors) {
        this.selectors = selectors;
    }

    public void setEndPoints(Map<String, List<EndPointPortVO>> endPoints) {
        this.endPoints = endPoints;
    }
}
