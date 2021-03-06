# Changelog
All notable changes to devops-service will be documented in this file.

## [0.21.0] 2020-03-04

#### Add
- Added "component management" function to the cluster module, which supports the installation and uninstallation of management monitoring components (Prometheus, Grafana, AlertManager)
- Added "cluster monitoring" function to the cluster module, and supports monitoring the resource usage of all nodes in the cluster on the premise that the monitoring components are installed
- Added the "node monitoring" function to the detail page of each node in the cluster. After installing the monitoring component, it supports viewing the resource usage details of each node and the resource usage of all pods under the node
- The cluster module has a new "health check" function and integrates Polaris components to support detection of configuration issues in the cluster and environment that may affect stability, reliability, scalability, and security
- Instance view: Added the function of "health check" of the environment in the environment layer, which supports detection of configuration problems in each instance configuration file that may affect stability, reliability, scalability, and security
- Resource view: Added "Commit Synchronization" display in the environment layer, which supports viewing the submission synchronization status and GitOps error log of the corresponding environment here
- New "Batch Deployment" function in deployment module, instance view and resource view, which supports the function of batch deployment of multiple application services to the same environment at the same time
- "Instance-Run Details -- More Details", added "YAML Format View" function, support to view details of instance configuration files in YAML format
- Added "Reset Gitlab Password" function in "Personal Center", which supports resetting GitLab password with one click

#### Fix
- Fixed an issue where Pods could not be added or deleted after the instance update failed
- Fixed the issue that every page of latest branch in continuous integration pipeline

#### Optimization
- Optimized the timeout logic of the application service creation process to avoid the situation that has been processing, resulting in the application service cannot be deleted
- Optimized pull shared application service image
- Optimized PV creation process, allowing users to directly assign permissions to specific projects to avoid incorrect binding
- Optimized the display problem of "Instance-Run Details" interface and improved the display of missing fields
- Added the status of clusters in the "owned cluster" column in the PV list
- Added "PV type" display to PVC list
- Added "Deployment Environment" column to the pipeline list to show the environment corresponding to the deployment tasks included in the pipeline

#### Remove
- Removed line chart of CPU and memory usage of Pods in "Resource View-Network Details" interface
- Removed the list of memory and CPU usage in "Resource View-Environment Layer"

## [0.20.0] 2019-12-09
#### Add
- Added "component management" function to the cluster module to support the management of the installation and uninstallation of CertManager components
- New PV management function in the cluster module, which supports the creation and management of NFS and HostPath PVs in the cluster
- Added manual resource configuration module in the manual deployment interface, which supports the creation of associated networks and domain names for corresponding instances during deployment
- Added the function of deleting Pods in the Pod details page of the resource module instance layer, which supports deleting a Pod in the instance
- Added full screen viewing when viewing instance events in the resource module instance layer
- Added the function of deleting the disabled application service in the application service module, which supports deleting the disabled application service
- Added "Recently used" shortcut in the drop-down box of the code management module to select the application service
- "Code Management" module to copy the warehouse address, added support for copying the SSH address of the warehouse
- New tab page of deployment configuration in the resource module "Instance view-environment layer", which supports people with environmental permissions to create and manage deployment configurations here
- Added a quick entry to view all execution records of this pipeline in the pipeline list
- Project layer-In the notification settings, new tab pages for agile messages, DevOps messages, and resource deletion verification are added. This page supports unified management of the sending methods and notification objects of various message notification events under the project
#### FIX
- Fixed issue where ciphertext was stored as plaintext in the environment library
- Fixed the problem of inconsistent project data of imported shared services
- Fixed the issue that when the first stage is empty in the manual trigger pipeline, the execution fails
- Fixed the problem of service encoding and name length limitation when importing application services
- Fixed an issue where the permission assignment interface must select a member when assigning permissions to specific members

#### optimization
- Optimized the logic of instance state and its corresponding Command state in the resource module instance layer
- The Harbor library at the project level is made private by default
- Optimized page jump after manual deployment
- Optimized the sorting problem in the branch list
- Optimized the status and corresponding operations in the environment configuration, and support the removal of the disabled and unconnected environments
- Optimized configuration mapping and cipher text status display in resource module
- Optimized the character limit of the app service name, relaxing it to 40 characters
- Optimized the structure and message type of "Personal Center-Reception Settings"

## [0.19.0] 2019-10-26
#### New
- Added "Application Service Details" interface, which contains details of an application service version, permission assignments, and sharing settings for the service
- Added "Share Settings" function of application service, allowing project owners to share a specific version or type of application service to other projects in the organization
- Add code libraries of other application services as templates to create an application service
- Add the function of batch importing application services shared in the organization to this project
- Added "Code Management" interface, which integrates all functions in the original development pipeline, retaining and highlighting common functions
- New environment details interface for environment configuration module, which includes GitOps logs, deployment configuration and permission allocation in the corresponding environment
- A new list of deployment records on the Deployment page, which includes all records and details generated by manual deployment and pipeline deployment
- Added "resources" interface, which includes instance view and resource view, supports viewing resource details in an environment from the dimensions of application services and resources
- Added "Custom Resources" interface in the "Resources" module, which supports adding and managing non-c7n-release type YAML files


#### fix
- Fixed instance code uniqueness check
- Fixed the issue that the gitops file could not be found in the interface deployment in the case of devops-service service multiple instances
- Fixed the issue of permission removal for disabled users in previous app services and environments
- Fixed an issue where clicking Title sorting in the filter table would report an error
- Fixed the problem of deleting the environment when the gitops library corresponding to the environment does not exist
- Fixed an issue with 500 status codes when searching for non-existing app versions
- Fixed an issue where the private service configuration associated with the application service was not created during deployment
- Fixed the issue that when the instance with the same name is created after the instance is deleted, the instance associated with the network is not updated
- Fixed the problem that domain name switching from common protocol to encryption protocol is invalid
#### Optimization
- "Application Services" module optimizes the steps of creating application services and supports the rapid creation of an application service
- Integrate the "Notification Settings" in the component settings to the "Resource Security Settings" in the environment details interface of the environment configuration module, and support the configuration of the notification method (email, intranet message or SMS) and notification objects for deletion events in the corresponding environment
- Optimized the "Application Service Deployment" page to support the quick deployment of instances on one page
- Optimized the "Upgrade Instance" function of the original instance interface and changed it to "Change instance" to support rollback or upgrade to any version of the application service corresponding to the instance
- Optimized the function logic of "redeploy" of the instance interface
- Optimized the record overview interface of the CD pipeline and integrated it into the deployment record list
- Moved the "Cluster Management" in the DevOps settings at the organization level to the "Deployment" module at the project level
- Moved the "Certificate Management" in the DevOps settings at the organization level to the "Deployment" module at the project level


#### Remove
- Removed application templates at the organization level. If you want to select a template to create an application service, you can click the "Import Application Service" button in the application service interface and select: Import from GitHub-System Preset Template
- Removed the component settings page in the project settings, supports directly modifying the default warehouse in the warehouse settings or advanced settings of the organization layer, project layer, application service layer
- Removed notification settings in project settings and moved them to environment configuration module
- Removed the application market module at the project level
- Removed the function of "replace instance" in manual deployment. Need to rollback or upgrade instance in "change instance" in instance interface

## [0.15.0] - 2019-03-19
###Added
- Added the feature of automatic deployment in the deployment pipeline
- Added the feature of elements setup and currently supports the creation of Docker repositories and Helm repositories for application selection in projects.
- Added the advanced settings in the pages of creating and editing application
###Changed
- Optimized the application and environment rights assignment module, the role of the project members assigned permissions in gitlab is changed to developer
- Optimized the error message after the fuse is executed in the creation operation of the platform
- Optimized display of cluster list at the organizational layer
- Optimized the deletion logic of the organization layer cluster, only the clusters without associated environments can be deleted
- Optimized display of operation log pages in the instance
- Optimized the diff effect of the Values ​​component, and supports switching editor mode to compare the addition, deletion, and modification of the code line.

###Fixed
- Fixed an issue where Dockerfile was missing after selecting some templates for import when importing apps
- Fixed an issue with incorrect error message when there was no permission in the branch interface 
- Fixed an issue where the agent was disconnected from devops-service but the cluster still showed normal 
- Fixed an issue with the IP selector in network editing
- Fixed an issue with incorrect prompt information when working with instances 
- Fixed an issue in the deployment pipeline that switched the top environment and made a page jump 
- Fixed an issue where clicking the Retry button error jump in the pipeline section of the build report 
- Fixed paging issue for organization layer cluster pages
###Removed
- Removed the ability to delete failed apps 
- Removed restrictions for app suspension: Can’t deactivate apps with linked instances

[0.14.0] - 2019-02-15
###Added
- Added the node list to view detailed information about cluster nodes in the cluster page.
- Added instance operation logs in the instance details section which support viewing detailed operation records of each instance. 
- Added the entry to build reports on the continuous integration card in the development console page pipeline module.
- Add the entry to the deployment report on the Deployment Overview page.
###Changed
- Optimized the logic to increase or decrease the number of Pods in the instance details, the number of Pods cannot be reduced to zero
- Optimized the logic and auto-refresh issues in the development console page pipeline section
- Changed the input box for adding NodePort to LoadBalancer to be non-required
- Modified the empty interface prompt copy when there is no branch
- Optimized the empty value display of the address value of the ingress in the instance details
- Optimized the logic of GitLab synchronization users, adding query operations before synchronization

###Fixed
- Fixed the issue where the selected options could not be forked in the filter table.
 - Fixed an issue where network module external IP update failed.
- Fixed a cache issue in the "Recent" section of the Development Pipeline Module application selection box.
- Fixed a logical issue with filter table filtering in the application management interface.
- Fixed an issue where GitLab failed to sync mailbox after platform update user mailbox.
- Fixed an issue where the version of the different cluster versions of StatefulSet was unreachable and the Agent Controller was compatible with different cluster versions.
- Fixed the issue about the request to the development console.
- Fixed a problem with the loss of the stage of the sonarqube in the pipeline.
- Fixed issue with suffix name format issues when exporting apps.
- Fixed an issue with name verification due to name verification when creating an environment.
- Fixed an issue where the instance name was empty when the instance was created.
- Fixed an issue where the job order in the pipeline in the development console was abnormal.
- Fixed an issue where the delete command was first popped up when deleting a cluster with an associated environment.

###Removed
- Removed the feature of clicking the step bar to back in the platform.

##[0.13.0] - 2019-01-07
###add
- New pipeline module for development console interface, including: branch management, continuous integration and application version; support for branch-centric development in the pipeline
- Added network settings for Endpoints type in the target object section of the network module
- Added support for LoadBalancer in the network configuration section of the network module
- Details of the instance details module added StatefulSet, DaemonSet, PVC, Service, and Ingress
- Added the addition and subtraction of Pod in the instance details to support the increase or decrease of the number of Pods directly on the current interface.
- The application management module adds the function of importing applications, supports importing existing applications from Github and GitLab libraries, and adds corresponding files to imported applications according to the selected application template.
- Add SpringBoot, Go application templates to predefined templates
- The container interface newly displays the name and status of each Container in each Pod

###change
- When creating a network, when the network configuration section selects NodePort, the node port is changed to non-required
- The container interface distinguishes the concept of Pod and container in detail, making the interface more intuitive
- Optimized the instance details interface to swap the order of instance events and run details modules
- Optimized the way in which the instance event interface Job and Pod states are displayed
- Optimized the process of uploading certificates, replacing the order in which Cert files and Key files are filled


###repair
- When the instance interface deployment instance is fixed, after selecting the application module, you cannot see the problem of the instance being deployed.
- Fixed issue with name validation when creating environment
- Fixed display issue with instances in target object when editing network
- Fixed an issue where the corresponding port was unchanged when re-selecting the network when creating a domain name
- Fixed an issue where some corresponding apps were not displayed after clicking the view container details through the instance interface.
- Fixed an issue where the log was lost after deployment failed in the automated test module
- Fixed an issue where the instance name was empty when the instance was created
- Fixed an issue where the instance event was an npe exception due to an empty operator
- Fixed an issue where the environment pipeline modified the environment packet error
- Fixed issue with webhook time zone
- Fixed a format issue caused by exporting points in app custom names
- Fixed an issue where the form content could still be edited after the create or modify operation was submitted

###Remove
- Removed the Networking display in the environment overview interface instance details


## [0.12.0] - 2018-12-14
### Add
- Added application development rights allocation to support specific development operators for each application
- Added configuration mapping to support adding configuration mappings in each environment
- Added ciphertext function to support adding ciphertext in each environment, which can be used to store k8s resources of small pieces of sensitive data, such as passwords, tokens, or keys.
- Added the management function of the organization-level certificate, the certificate created at the organization level, can be used when creating a certificate for each project under the organization
- Added instance events, support for viewing event records for each instance job and pod
- Add a new instance to the Deployment for more details, including: port, data volume, health check, host settings, environment variableDTOS, tags
- New test application creation, this type of application is only available for automated testing
- Added automatic refresh page, click on the corner next to all refresh buttons to set
- The CrtManager plugin is automatically added to the cluster to enable the environment under the cluster to use the certificate.
- Added view permissions in Dashboard and reports related to app permissions and environment permissions
- Added instance name custom function when creating new instance
- Added the name of the cluster to which the environment is connected on the environment card

### Change
- Optimized naming rules for tag names
- Optimized the display mode of the instance module, and displays the status and total number of pods in the instance column in the instance column.
- Optimized the style of the YMAL editor
- Optimized page logic after successful application deployment in the Environment Overview page.
- Optimized display of development pipeline and empty interface of report section


### Fix
- Fix the problem that the environment button disappears in the environment pipeline
- Fixed page logic problem after modifying environment name in environment pipeline
- Fixed an instance upgrade failure, and the related network status is not normal.
- Fixed a problem with Dashboard page dragging cards with blank pages
- Fixed persistent integration list, non-linkable jumps in the sonarqube phase
- Fixed a blank issue caused by missing English in the code submission report
- Fix issues caused by environment selection during application deployment
- Fixed an issue where the edit button and the disable button were not hidden after the creation of the app failed

### Remove
- Removed the deployment details module of the instance details interface

## [0.11.0] - 2018-11-16
### Added
- Added cluster management module to support the creation, editing and distribution of Kubernetes clusters
- Added permission allocation for the environment to support configuring specific operators for each environment
- Added the ability to delete the environment and support the deletion of the environment in the environment deactivated area.
- A new development console is added to the development pipeline, which integrates the main functions in the development pipeline to make development operations easier.
- Add an entry to view code quality in the development pipeline code repository
- The Dashboard page adds modules to quickly view branch status, code submissions, application builds and deployments, and provides a quick jump to the appropriate module entry.
- The deployment section adds a deployments layer and supports multiple deployments in a single chart file.
- New instance redeployment feature
- Error message for deployment failure in new report
### Changed
- Reorganize the optimized development pipeline structure and apply application-centric operations
- Reorganize the optimized deployment pipeline structure and operate in an environment-centric manner
- You must select an associated cluster when creating your environment
- Optimized the naming of application market export files and supports custom naming.
- Optimize and unify the various empty interfaces of the platform
- Optimized the delete operation prompt box, clearly indicating the deletion of the object name
- Improve the platform guide copy and strengthen the understanding of the primary users
- Optimized the target object content in the associated network list after deleting the instance
- Optimized the display problem of the version in the list after the instance upgrade failed or the new failed
- Optimized display of create action buttons at the top of the environment overview interface
- Optimized icon display for rapid deployment of the deployment overview interface
### Fixed
- Fixed an issue where the capitalization of the input letters was not resolved when editing the app name
- Fix logic issues when selecting instances and selecting apps when creating a network
- Fixed an issue where the replacement instance failed
- Fixed an issue that prevented operation after deployment timed out
- Fixed an issue where the environment was not verified when creating a domain name
- Fixed an issue that could not be processed after creating an application failed
### Removed
- Removed the deployment administrator role in the project and assigned all its permissions to the project owner
- Removed deployment instance and single app view from deployment pipeline instance management
- Removed upgrade instance and redeploy option after stopping instance

## [0.10.0] - 2018-09-16
### Added
- Added environment grouping to support viewing of pipelines by environment
- Added domain name certificate management function to support domain name certificate application and upload
- Added DevOps reporting capabilities to support view of code submission, application build, and application deployment
- Add a new deployment overview feature to see how all applications are deployed in each environment, and complete the rapid deployment of the latest version of the app on this page
- Support for using shell commands to manipulate pods for debugging
- Support for filling out notes when creating tags, and support viewing edits and modifications
- Added Stop Following and Go Top functions when viewing container logs, and supports full-screen view of container logs
- Add container interface Select environment and application drop-down box and application version interface Select application drop-down box for easy search and filtering
### Changed
- Optimized the status of container log long links
- Unified icon for status display within the system
- Relevant state optimization for service, domain name, instance, application deployment, etc.
- Optimized the loading speed of the overview interface
- Optimized pagination, filtering, sorting, and refreshing of tables
### Fixed
- Fix an issue that cannot be operated after a deployment timeout fails
- Fix the problem that the deployment part replacement instance has not been modified.
- Fix the 0.9.0 version service list prompt error, time component display error
- Fix the problem of instance status error in service editing
- Fix the problem of deleting the commit in the application template when creating the application
- Fix gitlab group creation failed when creating project with the same name
- Fix the problem that the icon of the page connection document is not uniform
- Fix the problem of adding port button disappearing in service creation
### Removed
- Remove the multi-application view from the deployment pipeline instance management

## [0.9.0] - 2018-08-17
### Added
- Refactor deploy procedure of GitOps
- Support to api  overview of thn environment
### Changed
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


[0.16.0] - 2019-04-19
Added
- The deployment pipeline module added the feature of pipelines to allow multiple phases in the pipeline, with multiple tasks added to each phase. Including automatic deployment tasks and manual card point tasks.
- Added a new pipeline execution overview page in the Deployment Pipeline to support viewing pipeline execution, process details, and approval history
- The Deployment Pipeline Module adds a new deployment configuration page that allows you to create a deployment configuration here for the automatic deployment task in the pipeline.
- CLI tools were added to support page operations in the command line execution platform
Fixed
- Fixed an issue with an error when deleting a network with a deployment error
- Fixed display issue with yaml editor error prompt
- Fixed an issue where automatic deployment of the same version of the deployment failed to replace multiple instances
- Fixed an issue where query tag failures occurred when creating tasks in agile management
- Fixed an issue where creating a harbor repository failed in component setup
- Fixed an issue where the external ip of the loadbalance type network was not returned.
- Fixed an issue where there were two underscores in the middle of the code when creating the app.
- Fixed an issue caused by committing without modifying the application when deploying the app
- Fixed a problem where each service configuration configMap did not have a successful retrace


Removed
- Removed the auto-deployment page from version 0.15.0 and built it into the pipeline to add the task section



[0.17.0] - 2019-05-20
Added
- Added a code quality page in the development pipeline module to support viewing the inspection results and specific details of the application in SonarQube
- Added code quality report in DevOps reports to view changes in bugs, security vulnerabilities, code smells, duplication and coverage in application code quality
- Added DevOps-notification setting function in the element-setting module to support configuring notification mode (mail, station letter or SMS) and notification object for deletion events in various environments.
- After the notification is successfully created in the DevOps-notification settings, when deleting resources such as instances in the environment, you need to enter the verification code obtained by the notification to perform secondary confirmation of the deletion operation.
- Added the entry for the setting project Harbor repository type in the element-setting page in the project-setting module.
- ConfigMap in the deployment pipeline module was allowed to create and edit as YMAL.


Fixed
- Fixed an issue where apps imported into the app market could be selected in the development console
- Fixed a problem with duplicate staff queries when creating a pipeline
- Fixed filtering of user selectors in the pipeline
- Fixed an issue where some applications failed to get the chart package when the app was exported.
- Fixed an issue where gitops executed saga transaction instances occasionally stuck
- Fixed an issue where the update application failed in the saga transaction logic that created the application.
- Fixed an issue where the annotation of objects in gitops was not retained
Changed
- Optimized display of unexecuted tasks in the pipeline details
- Optimized jumps for instances in deployment tasks within the pipeline details
- Optimized UI for the pipeline details interface
- Optimized permissions for project members with no environment permissions in the pipeline


[0.18.0] - 2019-06-18
Added
- Added internal message in the deployment pipeline module which used to inform relevant member to operate the pipeline.
- Added the status and operation button of the pipeline in the pipeline details page.
- Added "quick search" and "about me" filter boxes in the pipeline management page and pipeline record page.
- Added deployment configuration in the deployment pipeline enabling user to create deployment configuration for application deployment and to create automatic deployment tasks.
- Added instance-related service and ingress page in the instance section, which support to create and view instance-related service and ingress.
- Added another mode of uploading certificate in the certificate page, and added the verification of certificate file.
- Added the GitOps retry button in the environment overview page.

Fixed
- Fixed the bug of null value in the ConfigMap.
- Fixed the query problem with branches in the development console page.
- Fixed the issue of format in the Redis container.
- Fixed the problem caused by clicking retry button after pipeline failure.




Changed
- Optimized the query of code quality page in the development pipeline.
- Optimized the operation to create branches in the development pipeline.
- Optimized the authority problem of the pipeline.
- Optimized the query speed of the instance page.
- Optimized the problem of importing only master branches when importing applications from GitLab and Github, now all branches are imported by default.
- Optimized the query speed for released versions of applications in the application market.

Removed
- Removed the deployment configuration from the pipeline section and placed it in the deployment pipeline module.
