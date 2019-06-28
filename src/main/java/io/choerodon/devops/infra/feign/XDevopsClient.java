package io.choerodon.devops.infra.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "x-devops-service")
public interface XDevopsClient {

    @PostMapping(value = "/trace/socket_msg")
    ResponseEntity recordSocketMsg(@RequestParam("msgKey") String msgKey,
                                                    @RequestParam("helmType") String helmType,
                                                    @RequestParam("envId") Long envId,
                                                    @RequestBody String body);


    @PostMapping(value = "/trace/error_msg")
    ResponseEntity recordErrorMsg( @RequestParam String type
                            , @RequestParam(required = false) String token
                            , @RequestParam(required = false) Long envId
                            , @RequestParam(required = false) String sha
                            , @RequestParam(required = false) String releaseName
                            , @RequestBody String body);

}
