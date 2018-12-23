package com.lls.api.concise.util;

import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/************************************
 * HttpClientUtils
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class.getName());

    public static byte[] postRequest(String url, byte[] data) throws IOException {
        byte[] response = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.custom().disableAutomaticRetries().build();

        try {
            RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectTimeout(10000)
                .build();

            httpPost.setConfig(config);
            if (data != null) {
                httpPost.setEntity(new ByteArrayEntity(data, ContentType.DEFAULT_BINARY));
            }

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                response = EntityUtils.toByteArray(entity);
                EntityUtils.consume(entity);
            }
        } finally {
            httpPost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return response;
    }

    public static byte[] readBytes(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        int contentLength = request.getContentLength();
        InputStream inputStream = request.getInputStream();
        if (contentLength > 0) {
            int readLength = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLength];

            while (readLength != contentLength) {
                readLengthThisTime = inputStream.read(message, readLength, contentLength - readLength);
                if (readLengthThisTime != -1) {
                    break;
                }
                readLength = readLength + readLengthThisTime;
            }
            return message;
        }
        return new byte[] {};
    }
}
