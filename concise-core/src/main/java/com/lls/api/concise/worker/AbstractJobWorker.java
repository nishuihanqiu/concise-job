package com.lls.api.concise.worker;

import com.lls.api.concise.core.Configuration;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.registry.NodeConfig;
import com.lls.api.concise.registry.WorkerRegistry;
import com.lls.api.concise.rmi.RemoteServer;
import com.lls.api.concise.rmi.serialize.Serialization;
import com.lls.api.concise.rmi.serialize.SerializationContext;
import com.lls.api.concise.rmi.serialize.SerializationFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/************************************
 * AbstractJobWorker
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public abstract class AbstractJobWorker implements Worker {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected WorkerContext workerContext;
    protected RemoteServer remoteServer;
    protected WorkerRegistry workerRegistry;
    protected Configuration configuration;
    protected AtomicBoolean started = new AtomicBoolean(false);


    public AbstractJobWorker(Configuration configuration) {
        this.configuration = configuration;
        workerContext = this.generateWorkerContext();
        workerContext.setConfiguration(configuration);
        NodeConfig nodeConfig = this.createNodeConfig(configuration);
        workerContext.setNodeConfig(nodeConfig);
        SerializationContext serializationContext = new SerializationContext(createSerialization(configuration));
        workerContext.setSerializationContext(serializationContext);
    }

    private WorkerContext generateWorkerContext() {
         return new WorkerContext();
    }

    private NodeConfig createNodeConfig(Configuration configuration) {
        NodeConfig config = new NodeConfig();
        config.setAvailable(true);
        config.setWorkThreads(64);
        config.setNodeGroup("concise-job");
        config.setRegistryAddress(configuration.getAddress());
        config.setInvokeTimeoutMillis(1000 * 60);
        return config;
    }

    private Serialization createSerialization(Configuration configuration) {
        return SerializationFactory.newSerialization(configuration.getSerializationVersion());
    }

    @Override
    final public void start() {
        try {
            if (started.compareAndSet(false, true)) {
                initRegistry();
            }
        } catch (Throwable t) {
            logger.error("start failed ");
        }
    }

    private void initRegistry() {

    }

    @Override
    final public void destroy() {

    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public boolean isDestroy() {
        return false;
    }
}
