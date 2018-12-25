package com.lls.api.concise.runner;

import com.lls.api.concise.core.Result;
import com.lls.api.concise.job.Action;
import com.lls.api.concise.job.Job;
import com.lls.api.concise.job.JobContext;
import com.lls.api.concise.job.JobExtInfo;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.rmi.codec.Response;
import com.lls.api.concise.worker.WorkerContext;
import sun.nio.ch.Interruptible;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicBoolean;

/************************************
 * RunnerDelegate
 * @author liliangshan
 * @date 2018/12/24
 ************************************/
public class RunnerDelegate implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RunnerDelegate.class);
    private RunnerListener listener;
    private WorkerContext workerContext;
    private Interruptible interruptible;
    private Runner runner;
    private Job job;
    private AtomicBoolean interrupted = new AtomicBoolean(false);
    private Thread thread;

    public RunnerDelegate(WorkerContext workerContext, RunnerListener listener, Job job) {
        this.workerContext = workerContext;
        this.listener = listener;
        this.job = job;

        this.interruptible = thread -> RunnerDelegate.this.interrupt();
    }

    private void interrupt() {
        if (!interrupted.compareAndSet(false, true)) {
            return;
        }
        if (this.runner instanceof InterruptableRunner) {
            ((InterruptableRunner) this.runner).interrupt();
        }
    }


    @Override
    public void run() {
        thread = Thread.currentThread();

        try {
            blockedOn(interruptible);
            if (Thread.currentThread().isInterrupted()) {
                ((InterruptableAdapter) interruptible).interrupt();
            }

            Response response = new Response();
            long startTime = System.currentTimeMillis();
            try {
                workerContext.getRunnerPool().getRunnerManager().put(job.getJobId(), this);
                this.runner = workerContext.getRunnerPool().getRunnerFactory().newRunner();
                Result result = this.runner.execute(this.buildJobContext(job));
                if (result == null) {
                    response.setResult(Action.SUCCESS);
                } else {
                    if (result.getAction() == null) {
                        response.setAction(Action.SUCCESS);
                    } else {
                        response.setAction(result.getAction());
                    }
                    response.setResult(result);
                }

                long time = System.currentTimeMillis() - startTime;
                if (logger.isDebugEnabled()) {
                    logger.debug("Job execute completed : {}, time:{} ms.", job.getJobId(), time);
                }
                listener.onCompleted(response);
            } catch (Throwable t) {
                StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                response.setAction(Action.FAILED);
                response.setResult(sw.toString());
                long time = System.currentTimeMillis() - startTime;
                logger.error("Job execute error : {}, time: {}, {}", job.getJobId(), time, t.getMessage(), t);
                listener.onFailed(response, t);
            } finally {
                this.checkInterrupted();
                workerContext.getRunnerPool().getRunnerManager().remove(job.getJobId());
            }
        } finally {
            blockedOn(null);
        }
    }

    private static void blockedOn(Interruptible interruptible) {
        sun.misc.SharedSecrets.getJavaLangAccess().blockedOn(Thread.currentThread(), interruptible);
    }

    private void checkInterrupted() {
        try {
            if (isInterrupted()) {
                logger.info("SYSTEM:Interrupted");
            }
        } catch (Throwable t) {
            logger.warn("checkInterrupted error", t);
        }
    }

    private boolean isInterrupted() {
        return this.interrupted.get();
    }

    private JobContext buildJobContext(Job job) {
        JobContext jobContext = new JobContext();
        jobContext.setJob(job);
        JobExtInfo jobExtInfo = new JobExtInfo();
        jobExtInfo.setRepeatCount(job.getRepeatCount());
        jobExtInfo.setJobType(job.getJobType());
        jobExtInfo.setRetryTimes(job.getMaxRetryTimes());
        jobContext.setJobExtInfo(jobExtInfo);
        jobContext.setLogger(logger);
        return jobContext;
    }

    public Thread currentThread() {
        return thread;
    }

    public Job getJob() {
        return job;
    }

    private abstract class InterruptableAdapter implements Interruptible {
        @Override
        public void interrupt(Thread thread) {
            interrupt();
        }

        abstract void interrupt();
    }


}
