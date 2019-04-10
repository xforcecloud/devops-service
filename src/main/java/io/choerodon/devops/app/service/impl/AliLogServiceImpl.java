package io.choerodon.devops.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import io.choerodon.devops.app.service.AliLogService;
import io.choerodon.devops.domain.application.entity.ProjectE;
import io.choerodon.devops.domain.application.repository.IamRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static java.lang.System.exit;

/**
 * 版权：    上海云砺信息科技有限公司
 * 创建者:   youyifan
 * 创建时间: 4/9/2019 5:57 PM
 * 功能描述:
 * 修改历史:
 */
@Service
public class AliLogServiceImpl implements AliLogService {
    @Autowired
    private IamRepository iamRepository;

    String akId = "LTAIucoh28kS3Gqy";
    String ak = "2TiL4mP6XQXvuCrFx0NQE2pMmg2ibC";
    // 如何使用sts访问日志服务请参考：https://www.atatech.org/articles/101998
    String roleArn = "acs:ram::1605435682286633:role/xforcecloud-log";
    String roleSession = "xforcecloud-log-test";
    String stsHost = "sts.aliyuncs.com";
    String signInHost = "http://signin.aliyun.com";

    static String policy = "{\n" +
            "    \"Version\": \"1\",\n" +
            "    \"Statement\": [\n" +
            "        {\n" +
            "            \"Action\": [\n" +
            "                \"log:ListProject\"\n" +
            "            ],\n" +
            "            \"Resource\": \"acs:log:*:*:project/*\",\n" +
            "            \"Effect\": \"Allow\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Action\": [\n" +
            "                \"log:List*\"\n" +
            "            ],\n" +
            "            \"Resource\": \"acs:log:*:*:project/xforceplus-paas-log/logstore/*\",\n" +
            "            \"Effect\": \"Allow\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Action\": [\n" +
            "                \"log:Get*\",\n" +
            "                \"log:List*\",\n" +
            "                \"log:Post*\",\n" +
            "                \"log:Create*\"\n" +
            "            ],\n" +
            "            \"Resource\": [\n" +
            "                \"acs:log:*:*:project/xforceplus-paas-log/logstore/*\",\n" +
            "                \"acs:log:*:*:project/usage-metering-service/logstore/*\"\n" +
            "            ],\n" +
            "            \"Effect\": \"Allow\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Action\": [\n" +
            "                \"log:List*\"\n" +
            "            ],\n" +
            "            \"Resource\": [\n" +
            "                \"acs:log:*:*:project/xforceplus-paas-log/dashboard\",\n" +
            "                \"acs:log:*:*:project/xforceplus-paas-log/dashboard/*\",\n" +
            "                \"acs:log:*:*:project/usage-metering-service/dashboard\",\n" +
            "                \"acs:log:*:*:project/usage-metering-service/dashboard/*\"\n" +
            "            ],\n" +
            "            \"Effect\": \"Allow\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Action\": [\n" +
            "                \"log:Get*\",\n" +
            "                \"log:List*\",\n" +
            "                \"log:Create*\"\n" +
            "            ],\n" +
            "            \"Resource\": [\n" +
            "                \"acs:log:*:*:project/xforceplus-paas-log/savedsearch\",\n" +
            "                \"acs:log:*:*:project/xforceplus-paas-log/savedsearch/*\",\n" +
            "                \"acs:log:*:*:project/usage-metering-service/savedsearch\",\n" +
            "                \"acs:log:*:*:project/usage-metering-service/savedsearch/*\"\n" +
            "            ],\n" +
            "            \"Effect\": \"Allow\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Override
    public String createSignInUrl(Long projectId){
        String orgCode = "usage-metering-service";
        String proCode = "test";
        ProjectE projectE = iamRepository.queryIamProject(projectId);

        String signInUrl = "";
        try {
            DefaultProfile.addEndpoint("", "cn-hangzhou", "Sts", stsHost);
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", akId, ak);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            AssumeRoleRequest assumeRoleReq = new AssumeRoleRequest();
            assumeRoleReq.setRoleArn(roleArn);
            assumeRoleReq.setRoleSessionName(roleSession);
            assumeRoleReq.setMethod(MethodType.POST);
            assumeRoleReq.setDurationSeconds(3600L);
            // 默认可以不需要setPolicy，即申请获得角色的所有权限
//            assumeRoleReq.setPolicy(policy); // 权限示例参考链接：https://help.aliyun.com/document_detail/89676.html

            AssumeRoleResponse assumeRoleRes = client.getAcsResponse(assumeRoleReq);
            System.out.println(assumeRoleRes.getCredentials().getAccessKeyId());
            System.out.println(assumeRoleRes.getCredentials().getAccessKeySecret());
            System.out.println(assumeRoleRes.getCredentials().getExpiration());
            System.out.println(assumeRoleRes.getCredentials().getSecurityToken());

            // construct singin url
            String signInTokenUrl = signInHost + String.format(
                    "/federation?Action=GetSigninToken"
                            + "&AccessKeyId=%s"
                            + "&AccessKeySecret=%s"
                            + "&SecurityToken=%s&TicketType=mini",
                    URLEncoder.encode(assumeRoleRes.getCredentials().getAccessKeyId(), "utf-8"),
                    URLEncoder.encode(assumeRoleRes.getCredentials().getAccessKeySecret(), "utf-8"),
                    URLEncoder.encode(assumeRoleRes.getCredentials().getSecurityToken(), "utf-8")
            );

            System.out.println(signInTokenUrl);
            HttpGet signInGet = new HttpGet(signInTokenUrl);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(signInGet);
            String signInToken = "";
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String signInRes = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(signInRes);
                signInToken = JSON.parseObject(signInRes).getString("SigninToken");

                if (signInToken == null) {
                    System.out.println("Invalid response message, contains no SigninToken: " + signInRes);
                    exit(-1);
                }
            } else {
                System.out.println("Failed to retrieve signInToken");
                exit(-1);
            }

            // construct final url
            signInUrl = signInHost + String.format(
                    "/federation?Action=Login"
                            + "&LoginUrl=%s"
                            + "&Destination=%s"
                            + "&SigninToken=%s",
                    URLEncoder.encode("https://www.aliyun.com", "utf-8"),
                    URLEncoder.encode(String.format("https://sls4service.console.aliyun.com/next/project/%s/logsearch/%s?isShare=true&hideTopbar=true&hideSidebar=true", orgCode, proCode), "utf-8"),
                    URLEncoder.encode(signInToken, "utf-8"));
            System.out.println(signInUrl);
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return signInUrl;
    }
}
