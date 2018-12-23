package com.lls.api.concise.rpc.codec;

import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.rpc.RemotingContext;
import com.lls.api.concise.rpc.serialize.SerializationContext;
import com.lls.api.concise.util.HttpClientUtils;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/************************************
 * CodecHandler
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class CodecHandler extends AbstractHandler {

    private static final Logger logger = LoggerFactory.getLogger(CodecHandler.class);
    private RemotingContext remotingContext;
    private SerializationContext serializationContext;

    public CodecHandler(RemotingContext remotingContext, SerializationContext serializationContext) {
        this.remotingContext = remotingContext;
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
            byte[] requestBytes = HttpClientUtils.readBytes(request);
            if (requestBytes == null || requestBytes.length == 0) {
                Response resp = new Response();
                resp.setError("RpcRequest byte[] is null");
                return resp;
            }

            Request req = serializationContext.deserialize(requestBytes, Request.class);
            return remotingContext.invoke(req, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            Response resp = new Response();
            resp.setError("Server-error:" + e.getMessage());
            return resp;
        }
    }

}
