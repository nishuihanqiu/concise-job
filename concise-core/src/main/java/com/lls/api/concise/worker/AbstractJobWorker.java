package com.lls.api.concise.worker;

import com.lls.api.concise.core.Configuration;
import com.lls.api.concise.rpc.RemoteServer;

/************************************
 * AbstractJobWorker
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public abstract class AbstractJobWorker implements Worker {

    private Configuration configuration;
    private WorkerContext workerContext;
    private RemoteServer remoteServer;


    public AbstractJobWorker(Configuration configuration) {

    }


}
