package com.lls.api.concise.rmi.codec;

import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.rmi.RemoteContext;
import com.lls.api.concise.rmi.serialize.SerializationContext;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/************************************
 * CodecHandler
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class CodecHandler extends AbstractHandler {

    private static final Logger logger = LoggerFactory.getLogger(CodecHandler.class);
    private RemoteContext remoteContext;
    private SerializationContext serializationContext;

    public CodecHandler(RemoteContext remoteContext, SerializationContext serializationContext) {
        this.remoteContext = remoteContext;
        this.serializationContext = serializationContext;
    }

    @Override
    public void handle(String target, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Response resp = this.doInvoke(request);
        byte[] responseBytes = serializationContext.serialize(resp);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        OutputStream outputStream = response.getOutputStream();
        outputStream.write(responseBytes);
        outputStream.flush();
    }

    private Response doInvoke(HttpServletRequest request) {
        try {
            byte[] requestBytes = this.readBytes(request);
            if (requestBytes == null || requestBytes.length == 0) {
                Response resp = new Response();
                resp.setError("RpcRequest byte[] is null");
                return resp;
            }

            Request req = serializationContext.deserialize(requestBytes, Request.class);
            return remoteContext.invoke(req, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            Response resp = new Response();
            resp.setError("Server-error:" + e.getMessage());
            return resp;
        }
    }

    private byte[] readBytes(HttpServletRequest request) throws IOException {
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
