package com.lls.api.concise.runner;

import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.worker.WorkerContext;

/************************************
 * DefaultRunnerFactory
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class DefaultRunnerFactory implements RunnerFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRunnerFactory.class);
    private WorkerContext context;

    public DefaultRunnerFactory(WorkerContext context) {
        this.context = context;
    }

    @Override
    public Runner newRunner() {
        try {
            return (Runner) context.getJobRunnerClazz().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
