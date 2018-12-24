package com.lls.api.concise.worker;

import com.lls.api.concise.core.Configuration;
import com.lls.api.concise.core.Context;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.registry.AbstractRegistry;

/************************************
 * RegistryWorker
 * @author liliangshan
 * @date 2018/12/24
 ************************************/
public class RegistryWorker extends AbstractRegistry implements Worker {

    private static final Logger logger = LoggerFactory.getLogger(RegistryWorker.class);
    private static volatile RegistryWorker instance;
    private static final Object lock = RegistryWorker.class;

    private Thread registryThread;
    private volatile boolean isStopped = false;
    private volatile boolean isStarted = false;
    private Configuration configuration;

    public RegistryWorker(Context context) {
        super(context);
        this.configuration = context.getConfiguration();
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void isStarted() {

    }

    @Override
    public void isStopped() {

    }


}
