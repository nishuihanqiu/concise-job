package com.lls.api.concise.worker;

import com.lls.api.concise.job.JobContext;
import com.lls.api.concise.logging.LoggingLevel;
import com.lls.api.concise.rpc.RemotingClient;
import com.lls.api.concise.runner.RunnerFactory;
import com.lls.api.concise.runner.RunnerPool;

/************************************
 * WorkerContext
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class WorkerContext extends JobContext {

  private RemotingClient remotingClient;

  private RunnerPool runnerPool;

  private RunnerFactory runnerFactory;

  private Class<?> jobRunnerClazz;

  private LoggingLevel level;


  public RemotingClient getRemotingClient() {
    return remotingClient;
  }

  public void setRemotingClient(RemotingClient remotingClient) {
    this.remotingClient = remotingClient;
  }

  public RunnerPool getRunnerPool() {
    return runnerPool;
  }

  public void setRunnerPool(RunnerPool runnerPool) {
    this.runnerPool = runnerPool;
  }

  public RunnerFactory getRunnerFactory() {
    return runnerFactory;
  }

  public void setRunnerFactory(RunnerFactory runnerFactory) {
    this.runnerFactory = runnerFactory;
  }

  public Class<?> getJobRunnerClazz() {
    return jobRunnerClazz;
  }

  public void setJobRunnerClazz(Class<?> jobRunnerClazz) {
    this.jobRunnerClazz = jobRunnerClazz;
  }

  public LoggingLevel getLevel() {
    return level;
  }

  public void setLevel(LoggingLevel level) {
    this.level = level;
  }
}
