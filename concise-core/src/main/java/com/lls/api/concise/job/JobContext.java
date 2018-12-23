package com.lls.api.concise.job;

import com.lls.api.concise.logging.Logger;

/************************************
 * JobContext
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public class JobContext {

  private Job job;

  private JobExtInfo jobExtInfo;

  private Logger logger;

  public Job getJob() {
    return job;
  }

  public void setJob(Job job) {
    this.job = job;
  }

  public JobExtInfo getJobExtInfo() {
    return jobExtInfo;
  }

  public void setJobExtInfo(JobExtInfo jobExtInfo) {
    this.jobExtInfo = jobExtInfo;
  }

  public Logger getLogger() {
    return logger;
  }

  public void setLogger(Logger logger) {
    this.logger = logger;
  }

  @Override
  public String toString() {
    return "JobContext{" +
      "job=" + job +
      ", jobExtInfo=" + jobExtInfo +
      ", logger=" + logger +
      '}';
  }
}
