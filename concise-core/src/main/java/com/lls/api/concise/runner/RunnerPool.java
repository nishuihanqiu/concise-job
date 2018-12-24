package com.lls.api.concise.runner;

import com.lls.api.concise.core.NamedThreadFactory;
import com.lls.api.concise.exception.NoAvailableRunnerException;
import com.lls.api.concise.job.Job;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.worker.WorkerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/************************************
 * RunnerPool
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class RunnerPool {

    private static final Logger logger = LoggerFactory.getLogger(RunnerPool.class);

    private ThreadPoolExecutor threadPoolExecutor;
    private RunnerFactory runnerFactory;
    private WorkerContext workerContext;
    private RunnerManager runnerManager;

    public RunnerPool(WorkerContext workerContext) {
        this.workerContext = workerContext;
        this.runnerManager = new RunnerManager();
        this.threadPoolExecutor = initThreadPoolExecutor();
        this.runnerFactory = workerContext.getRunnerFactory();
        if (this.runnerFactory == null) {
            this.runnerFactory = new DefaultRunnerFactory(workerContext);
        }
    }

    private ThreadPoolExecutor initThreadPoolExecutor() {
        int workThreads = workerContext.getNodeConfig().getWorkThreads();
        return new ThreadPoolExecutor(workThreads, workThreads, 30, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),           // 直接提交给线程而不保持它们
            new NamedThreadFactory("runner-pool"),
            new ThreadPoolExecutor.AbortPolicy());
    }

    public void execute(Job job, RunnerListener listener) throws NoAvailableRunnerException {
        try {
            threadPoolExecutor.execute(new RunnerDelegate(workerContext, listener, job));
            if (logger.isDebugEnabled()) {
                logger.debug("receive job success ! " + job);
            }
        } catch (RejectedExecutionException e) {
            logger.warn("no more thread to run job .");
            throw new NoAvailableRunnerException(e);
        }
    }

    public int getAvailablePoolSize() {
        return threadPoolExecutor.getMaximumPoolSize() - threadPoolExecutor.getActiveCount();
    }

    public void setWorkThread(int workThread) {
        if (workThread == 0) {
            throw new IllegalArgumentException("workThread can not be zero!");
        }

        threadPoolExecutor.setMaximumPoolSize(workThread);
        threadPoolExecutor.setCorePoolSize(workThread);

        logger.info("workThread update to {}", workThread);
    }

    public int getWorkThread() {
        return threadPoolExecutor.getCorePoolSize();
    }

    public void stopWorking() {
        try {
            threadPoolExecutor.shutdownNow();
            TimeUnit.SECONDS.sleep(1);
            threadPoolExecutor = initThreadPoolExecutor();
            logger.info("stop working succeed ");
        } catch (Throwable e) {
            logger.error("stop working failed ", e);
        }
    }

    public void shutdown() {
        try {
            threadPoolExecutor.shutdownNow();
            logger.info("stop working succeed ");
        } catch (Throwable t) {
            logger.error("stop working failed ", t);
        }
    }

    public RunnerFactory getRunnerFactory() {
        return runnerFactory;
    }

    public WorkerContext getWorkerContext() {
        return workerContext;
    }

    public class RunnerManager {
        private final ConcurrentMap<String, RunnerDelegate> RUNNERS = new ConcurrentHashMap<>();

        public void put(String jobId, RunnerDelegate delegate) {
            RUNNERS.putIfAbsent(jobId, delegate);
        }

        public void remove(String jobId) {
            RUNNERS.remove(jobId);
        }

        public boolean isExists(String jobId) {
            return RUNNERS.containsKey(jobId);
        }

        public List<String> getNotExists(List<String> jobIds) {
            if (logger.isDebugEnabled()) {
                logger.debug("Ask jobs: " + jobIds + " Running jobs ：" + RUNNERS.keySet());
            }
            List<String> notExistList = new ArrayList<>();
            for (String jobId : jobIds) {
                if (!isExists(jobId)) {
                    notExistList.add(jobId);
                }
            }
            return notExistList;
        }
    }

    public RunnerManager getRunnerManager() {
        return runnerManager;
    }
}
