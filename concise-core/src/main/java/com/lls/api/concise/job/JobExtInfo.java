package com.lls.api.concise.job;

/************************************
 * JobExtInfo
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class JobExtInfo {

  private int retryTimes = 0;
  private int repeatCount = 0;
  private boolean retry = false;
  private JobType jobType;
  private String seqId;

  public int getRetryTimes() {
    return retryTimes;
  }

  public void setRetryTimes(int retryTimes) {
    this.retryTimes = retryTimes;
  }

  public int getRepeatCount() {
    return repeatCount;
  }

  public void setRepeatCount(int repeatCount) {
    this.repeatCount = repeatCount;
  }

  public boolean isRetry() {
    return retry;
  }

  public void setRetry(boolean retry) {
    this.retry = retry;
  }

  public JobType getJobType() {
    return jobType;
  }

  public void setJobType(JobType jobType) {
    this.jobType = jobType;
  }

  public String getSeqId() {
    return seqId;
  }

  public void setSeqId(String seqId) {
    this.seqId = seqId;
  }
}
