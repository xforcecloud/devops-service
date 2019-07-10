package io.choerodon.devops.infra.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "x-devops-service")
public interface XDevopsClient {

    //TODO
    @PostMapping(value = "/trace/socket_msg")
    ResponseEntity recordSocketMsg(@RequestParam("msgKey") String msgKey,
                                                    @RequestParam("helmType") String helmType,
                                                    @RequestParam(value = "envId", required = false) Long envId,
                                                    @RequestBody String body);


    @PostMapping(value = "/trace/error_msg")
    ResponseEntity recordErrorMsg( @RequestParam("type") String type
                            , @RequestParam(value = "token", required = false) String token
                            , @RequestParam(value = "envId", required = false) Long envId
                            , @RequestParam(value = "sha", required = false) String sha
                            , @RequestParam(value = "releaseName", required = false) String releaseName
                            , @RequestBody String body);


    @PostMapping(value = "/trace/msg")
    ResponseEntity recordEvent(
            @RequestParam("type") String type
            , @RequestParam(value = "envId") Long envId
            , @RequestParam(value= "releaseName") String releaseName
            , @RequestBody String body);

}
