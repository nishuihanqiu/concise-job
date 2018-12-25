package com.lls.api.concise.rmi;

import com.lls.api.concise.core.Configuration;
import com.lls.api.concise.exception.RemotingException;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.rmi.codec.CodecHandler;
import com.lls.api.concise.rmi.serialize.SerializationContext;
import com.lls.api.concise.util.HelperUtils;
import com.lls.api.concise.worker.WorkerContext;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;

import java.io.IOException;
import java.net.ServerSocket;

/************************************
 * JettyRemoteServer
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class JettyRemoteServer extends AbstractRemoteServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyRemoteServer.class);
    private Server server;
    private Thread thread;
    private SerializationContext serializationContext;

    public JettyRemoteServer(Configuration configuration, WorkerContext context) {
        super(configuration, context);
        initialize();
    }

    private void initialize() {
        serializationContext = this.workerContext.getSerializationContext();
        this.server = buildServer();
        this.thread = new Thread(this::startServer);
    }

    private Server buildServer() {
        if (configuration == null) {
            throw new IllegalStateException("must be set configuration");
        }

        String host = configuration.getHost();
        int port = configuration.getPort() > 0 ? configuration.getPort() : this.generateNetworkPort(9999);
        configuration.setPort(port);

        Server server = new Server(new ExecutorThreadPool());
        // http connector
        ServerConnector connector = new ServerConnector(server);
        if (host != null && host.trim().length() > 0) {
            connector.setHost(host);
        }
        connector.setPort(port);
        server.setConnectors(new Connector[]{connector});

        HandlerCollection collection = new HandlerCollection();
        collection.setHandlers(new Handler[]{new CodecHandler(remoteContext, serializationContext)});
        server.setHandler(collection);

        return server;
    }

    private void startServer() {
        try {
            server.start();
            logger.info("jetty remote server start success.");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            logger.info("finally");
        }
    }


    @Override
    protected void beforeServerStart() {
        thread.setDaemon(true);
    }

    @Override
    protected void onServerStart() {
        thread.start();
    }

    @Override
    protected void afterServerStart() {
        logger.info("jetty remote server is running.");
    }

    @Override
    protected void beforeServerDestroy() {

    }

    @Override
    protected void onServerDestroy() {

    }

    @Override
    protected void afterServerDestroy() {

    }

    private int generateNetworkPort(int defaultPort) {
        int port = defaultPort;
        while (port < 65535) {
            if (!isUsedPort(port)) {
                return port;
            }
            port++;
        }

        port = --defaultPort;
        while (port > 0) {
            if (isUsedPort(port)) {
                port--;
                continue;
            }
            return port;
        }

        throw new RemotingException("jetty remote server no available port.");
    }

    private boolean isUsedPort(int port) {
        boolean used;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            used = false;
        } catch (IOException e) {
            logger.debug("jetty remote server port:{} is in used", port);
            used = true;
        } finally {
            HelperUtils.close(serverSocket);
        }

        return used;
    }

}
