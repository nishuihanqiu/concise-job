package com.lls.api.concise.rmi;

import com.lls.api.concise.exception.RemotingException;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.rmi.codec.Request;
import com.lls.api.concise.rmi.codec.Response;
import com.lls.api.concise.rmi.serialize.SerializationContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;

/************************************
 * JettyRemoteClient
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class JettyRemoteClient extends AbstractRemoteClient {

    private static final Logger logger = LoggerFactory.getLogger(JettyRemoteClient.class);
    private CloseableHttpClient httpClient;
    private RequestConfig config;
    private SerializationContext serializationContext;
    private HttpPost httpPost;

    public JettyRemoteClient(SerializationContext context) {
        this.serializationContext = context;
    }

    @Override
    protected void beforeStart() {
        logger.info("jetty remote client completed of before start.");
    }

    @Override
    protected void onStart() {
        config = RequestConfig.custom()
            .setConnectionRequestTimeout(10000)
            .setSocketTimeout(10000)
            .setConnectTimeout(10000)
            .build();
        httpClient = HttpClients.custom()
            .setDefaultRequestConfig(config)
            .disableAutomaticRetries().build();
        httpPost = new HttpPost();
        logger.info("jetty remote client start success.");
    }

    @Override
    protected void afterStart() {
        logger.info("jetty remote client completed of after start.");
    }

    @Override
    public Response send(Request request) {
        if (!this.isStarted()) {
            throw new RemotingException("client not init.");
        }

        try {

            byte[] requestBytes = serializationContext.serialize(request);
            String url = request.getServerAddress();
            if (url != null && !url.toLowerCase().contains("http")) {
                url = "http://" + request.getServerAddress() + "/";
            }

            byte[] responseBytes = this.postRequest(url, requestBytes);
            if (responseBytes == null || responseBytes.length == 0) {
                Response response = new Response();
                response.setError("Network request fail, RpcResponse byte[] is null");
                return response;
            }

            return serializationContext.deserialize(responseBytes, Response.class);
        } catch (IOException e) {
            logger.error("send request error:" + e.getMessage(), e);
            Response response = new Response();
            response.setError("Network request error: " + e.getMessage());
            return response;
        }
    }

    @Override
    protected void beforeDestroy() {
        logger.info("jetty remote client completed of before destroy.");
    }

    @Override
    protected void onDestroy() {
        try {
            if (!httpPost.isAborted()) {
                httpPost.abort();
            }
            httpClient.close();
        } catch (IOException e) {
            logger.error("jetty remote client destroy error:" + e.getMessage(), e);
        }
    }

    @Override
    protected void afterDestroy() {
        logger.info("jetty remote client completed of after destroy.");
    }

    private byte[] postRequest(String url, byte[] data) throws IOException {
        byte[] response = null;
        httpPost.setURI(URI.create(url));
        httpPost.setConfig(config);
        if (data != null) {
            httpPost.setEntity(new ByteArrayEntity(data, ContentType.DEFAULT_BINARY));
        }
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                response = EntityUtils.toByteArray(entity);
                EntityUtils.consume(entity);
            }
            return response;
        } finally {
            httpPost.releaseConnection();
        }
    }


}
