package io.choerodon.devops.infra.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import io.choerodon.core.exception.CommonException;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

    HttpClientUtil() {
    }

    /**
     * 下载 tgz
     *
     * @param getUrl  tgz路径
     * @param fileUrl 目标路径
     */
    public static void getTgz(String getUrl, String fileUrl) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(getUrl);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try (FileOutputStream fos = new FileOutputStream(fileUrl)) {
                InputStream is = response1.getEntity().getContent();
                byte[] buffer = new byte[4096];
                int r = 0;
                while ((r = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, r);
                }
                is.close();
            }
        } catch (IOException e) {
            throw new CommonException(e.getMessage());
        }
    }


    public static Integer getSonar(String sonarUrl) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(sonarUrl);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            return response1.getStatusLine().getStatusCode();
        } catch (IOException e) {
            throw new CommonException(e.getMessage());
        }
    }

    /**
     * 上传 tgz
     *
     * @param postUrl  tgz路径
     * @param fileUrl 目标路径
     */
    public static void postTgz(String postUrl, String fileUrl) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(postUrl);
            FileBody bin = new FileBody(new File(fileUrl));
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart(bin.getFile().getName(), bin).build();
            httpPost.setEntity(reqEntity);

            CloseableHttpResponse response = httpclient.execute(httpPost);

            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
            }
            EntityUtils.consume(resEntity);
        } catch (IOException e) {
            throw new CommonException(e.getMessage());
        }
    }

}
