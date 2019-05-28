package io.choerodon.devops.api.dto;

public class DevopsEnvQuotaDTO {
    private Long envId;

    private String cpu;

    private String mem;

    private String pod;

    private String svc;

    private String cpuLimit;

    private String memLimit;

    private String podLimit;

    private String svcLimit;

    private String name;

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public String getCpuLimit() {
        return cpuLimit;
    }

    public void setCpuLimit(String cpuLimit) {
        this.cpuLimit = cpuLimit;
    }

    public String getMemLimit() {
        return memLimit;
    }

    public void setMemLimit(String memLimit) {
        this.memLimit = memLimit;
    }

    public String getPodLimit() {
        return podLimit;
    }

    public void setPodLimit(String podLimit) {
        this.podLimit = podLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSvc() {
        return svc;
    }

    public void setSvc(String svc) {
        this.svc = svc;
    }

    public String getSvcLimit() {
        return svcLimit;
    }

    public void setSvcLimit(String svcLimit) {
        this.svcLimit = svcLimit;
    }
}
