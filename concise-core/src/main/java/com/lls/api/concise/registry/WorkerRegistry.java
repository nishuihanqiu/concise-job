package com.lls.api.concise.registry;

import com.lls.api.concise.core.Configuration;
import com.lls.api.concise.core.Context;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.worker.Worker;

import java.util.concurrent.atomic.AtomicBoolean;

/************************************
 * WorkerRegistry
 * @author liliangshan
 * @date 2018/12/24
 ************************************/
public class WorkerRegistry extends AbstractRegistry {

    private static final Logger logger = LoggerFactory.getLogger(WorkerRegistry.class);
    private Thread registryThread;
    private AtomicBoolean started = new AtomicBoolean(false);
    private Configuration configuration;


    public WorkerRegistry(Context context) {
        super(context);
        this.configuration = context.getConfiguration();
    }

    @Override
    public void destroy() {
        if (started.compareAndSet(true, false)) {

        }
    }

    @Override
    protected void doRegistered(RegistryConfig registryConfig) {

    }

    @Override
    protected void doUnregistered(RegistryConfig registryConfig) {

    }

    @Override
    protected void doSubscribed(RegistryConfig config, NotifyEventListener listener) {

    }

    @Override
    protected void doUnsubscribed(RegistryConfig config, NotifyEventListener listener) {

    }

}
