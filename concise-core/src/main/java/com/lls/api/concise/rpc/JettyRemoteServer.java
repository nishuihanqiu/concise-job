package com.lls.api.concise.rpc;

import com.lls.api.concise.core.Configuration;
import com.lls.api.concise.core.Registry;
import com.lls.api.concise.core.RegistryConfig;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.worker.WorkerContext;

/************************************
 * JettyRemoteServer
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class JettyRemoteServer extends AbstractRemoteServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyRemoteServer.class);
    private Registry registry;
    private RegistryConfig registryConfig;
    private Configuration configuration;
    private WorkerContext workerContext;

    public JettyRemoteServer() {
        workerContext = new WorkerContext();
    }

    @Override
    protected void beforeServerStart() {

    }

    @Override
    protected void onServerStart() {

    }

    @Override
    protected void afterServerStart() {

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

}
