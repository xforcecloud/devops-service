# DevOps Service
`DevOps Service` is the core service of Choerodon. Current version: `0.11.1` 

Integrated several open source tools to automate the process of `planning`, `coding`, `building`, `testing`, and `deployment`, `operation`, `monitoring`.
 After a little simple configuration, then you'll get the most smoothest development experience.

此为基于原有的0.11.1版本切出的分支,主要是修改了对于 websocket-helper 的依赖,使用了新的 0.7.1-RELEASE-HEALTHCHECK 版本.
修正了对于 agent 的连接健康检查的问题.并增加了如下的配置参数.
```yaml
choerodon:
  websocket:
    maxMessageBufferSize: 512000 # 最大可以接受的消息大小,字节.
    healthCheckDuration: 30000 # 两次健康检测之间的时间隔间,毫秒.
    healthCheckTimeout: 6000 # 健康检测容忍的最长时间,超过认为失败.(毫秒)
    healthCheckTryNumber: 3 # 健康检查容忍的失败次数上限.
    healthCheckWorkerNumber: {cpu+1} # 健康检查可以使用的线程数量,默认为 CPU 核数+1.
```

## Feature
`DevOps Service` contains features as follows:
- Application management
- Version control (Using gitflow workflow)
- Application version management
- CI/CD dashboard
- Deploy management
- Ingress management

## Requirements
- Java8
- [GitLab Service](https://github.com/choerodon/gitlab-service)
- [Iam Service](https://github.com/choerodon/iam-service)
- [Harbor](https://vmware.github.io/harbor/cn/)
- [Kubenetes](https://kubernetes.io/)
- [MySQL](https://www.mysql.com)
- [Kafka](https://kafka.apache.org)

## Installation and Getting Started
1. init database

    ```sql
    CREATE USER 'choerodon'@'%' IDENTIFIED BY "choerodon";
    CREATE DATABASE devops_service DEFAULT CHARACTER SET utf8;
    GRANT ALL PRIVILEGES ON devops_service.* TO choerodon@'%';
    FLUSH PRIVILEGES;
    ```
1. run command `sh init-local-database.sh`
1. run command as follow or run `DevopsServiceApplication` in IntelliJ IDEA

    ```bash
    mvn clean spring-boot:run
    ```

## Dependencies
- `go-register-server`: Register server
- `config-server`：Configure server
- `kafka`
- `mysql`: devops_service database

## Reporting Issues
If you find any shortcomings or bugs, please describe them in the  [issue](https://github.com/choerodon/choerodon/issues/new?template=issue_template.md).

## How to Contribute
Pull requests are welcome! [Follow](https://github.com/choerodon/choerodon/blob/master/CONTRIBUTING.md) to know for more information on how to contribute.
