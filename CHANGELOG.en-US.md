# Changelog
All notable changes to devops-service will be documented in this file.

## [0.9.0] - 2018-08-17
### ADD
- Refactor deploy procedure of GitOps
- Support to api  overview of thn environment
### CHANGE
- Optimize replace instance values to support standard yaml format
- NetWork support multi port and support node port type network

## [0.8.0] - 2018-07-20
### Added
- `Repository`, `branch management`, `tag` and `merge request`, achieving more flexible branch management models.
- Connect with `agile management` to achieve consistency in agile issue management and DevOps code management.
- Integrated the webhook of push and merge request in `branch management`.
- Job operation event message in `container`. 
- Only save delta `values` config in db and support to save additional key in `values`.
- Code quality checking in CI pipeline.
- Sonarqube link in `application management`.
- Surport smooth upgrade from `0.7.0` to `0.8.0`.

### Changed
- Modify the naming rules for the version when running CI.
- Only save modified parts in `values`.

### Removed
- `Branch management` in `application management`.

### Fixed
- Problem with select-all in selection box.
- Filter condition of the table component cannot be cleared when the parent component is refreshed.
- Fixed switch version does not clear instances when modify the service in service management.
- Fixed instance details, the log not changed while switch the stage.

## [0.7.0] - 2018-06-29
### Added
- `Service management` instanseinstance availability verification.
- `Service management` port legality verification.
- `Ingress management` service availability verification.
- `Ingress management` path address uniqueness verification.
- Cancel button added to `application release` and `application deployment`.
- Yaml configuration file check and error information display.
- Chinese and English mode supported.

### Changed
- Chinese-English translation supported for backend error.
- Two api name changed so as to conform to naming and authority check.
- Optimized predefined application template acquisition for users, manual template creation no longer needed.

### Fixed
- Values replacement disorder in application deployment.
- Inaccurate time in Continuous Integration (CI) pipeline.
- Application detail README file unavailability in occasional circumstances.


## [0.6.0] - 2018-06-10
### Added
- `Release management` section, including `application release` and `application market`. 
- Service/Ingress status, operation type and status in `service` and `ingress` to track their running conditions.
- `Container log` in `container` to track its running status. 
- Review function in `application deployment` for users to check their operation. 
- Upgrade reminder of env-agent in `environment pipeline`. 
- Resource consistency mechanism in env-agent.
- Message sending failure and timeout confirmation mechanism in env-agent. 

### Changed
- Reconfigure `application deployment`, delete instance query and add `application instance`. 
- Distinguish Service domestic port and target port in `Service`.
- Update three predefined `application templates` to be more useable. 
- Modify some APIs based on the more standardized naming rule. 
- Optimize the instance scan mechanism.

### Removed
- "Rapid Deployment" functionalities.

### Fixed
- Logical bug in version check and error shown in frontend in `branch management`. 
- Inconsistent status after devops-service and env-agent restart.
- Orgnization admin not in Gitlab template group.
- Some other bugs.