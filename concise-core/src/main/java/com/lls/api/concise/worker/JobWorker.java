package com.lls.api.concise.worker;

import com.lls.api.concise.core.Configuration;
import com.lls.api.concise.registry.WorkerRegistry;
import com.lls.api.concise.rmi.JettyRemoteClient;
import com.lls.api.concise.rmi.JettyRemoteServer;
import com.lls.api.concise.rmi.RemoteClient;
import com.lls.api.concise.rmi.RemoteServer;
import com.lls.api.concise.runner.RunnerFactory;
import com.lls.api.concise.runner.RunnerPool;

/************************************
 * JobWorker
 * @author liliangshan
 * @date 2018/12/24
 ************************************/
public class JobWorker extends AbstractJobWorker {

    private RemoteClient remoteClient;
    protected RemoteServer remoteServer;
    protected WorkerRegistry workerRegistry;
    private LoggingWorker loggingWorker;

    public JobWorker(Configuration configuration) {
        super(configuration);
        loggingWorker = LoggingWorker.getInstance();
    }

    @Override
    protected void beforeStart() {
        remoteClient = new JettyRemoteClient(workerContext.getSerializationContext());
        workerContext.setRemoteClient(remoteClient);
        workerContext.setRunnerPool(new RunnerPool(workerContext));
        remoteServer = new JettyRemoteServer(configuration, workerContext);
    }

    @Override
    protected void onStart() {
        remoteServer.start();
        remoteClient.start();
        loggingWorker.start();
    }

    @Override
    protected void afterStart() {
        logger.info("job worker start success.");
    }

    @Override
    protected void beforeDestroy() {

    }

    @Override
    protected void onDestroy() {

    }

    @Override
    protected void afterDestroy() {

    }

    public void setRunnerFactory(RunnerFactory factory) {
        workerContext.setRunnerFactory(factory);
    }

    public void setWorkThreads(int workThreads) {
        config.setWorkThreads(workThreads);
    }

    public void setNodeGroup(String nodeGroup) {
        config.setNodeGroup(nodeGroup);
    }

    public void setRegistryAddress(String registryAddress) {
        config.setRegistryAddress(registryAddress);
    }

}
