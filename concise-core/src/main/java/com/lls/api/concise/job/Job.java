package com.lls.api.concise.job;


import com.lls.api.concise.annotation.NotNull;

import java.io.Serializable;
import java.util.Map;

/************************************
 * Job
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public class Job implements Serializable {

    private static final long serialVersionUID = 18849993882329L;

    @NotNull
    private String jobId;
    private Integer priority = 0;
    private Map<String, String> extParams;
    private boolean feedback;
    private int maxRetryTimes = 0;
    private String cronExpression;
    private int repeatCount = 0;
    private long repeatInterval = 0;
    private Long triggerTime;
    private String blockStrategy;
    private JobType jobType;

    private Long loggingId;
    private long loggingTime;
    private String groovyCategory;
    private String groovySource;
    private long groovyUpdatedAt;

    private int broadcastIndex;
    private int broadcastTotal;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Map<String, String> getExtParams() {
        return extParams;
    }

    public void setExtParams(Map<String, String> extParams) {
        this.extParams = extParams;
    }

    public boolean isFeedback() {
        return feedback;
    }

    public void setFeedback(boolean feedback) {
        this.feedback = feedback;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public Long getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Long triggerTime) {
        this.triggerTime = triggerTime;
    }

    public String getBlockStrategy() {
        return blockStrategy;
    }

    public void setBlockStrategy(String blockStrategy) {
        this.blockStrategy = blockStrategy;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public JobType getJobType() {
        return jobType;
    }

    public Long getLoggingId() {
        return loggingId;
    }

    public void setLoggingId(Long loggingId) {
        this.loggingId = loggingId;
    }

    public long getLoggingTime() {
        return loggingTime;
    }

    public void setLoggingTime(long loggingTime) {
        this.loggingTime = loggingTime;
    }

    public String getGroovyCategory() {
        return groovyCategory;
    }

    public void setGroovyCategory(String groovyCategory) {
        this.groovyCategory = groovyCategory;
    }

    public String getGroovySource() {
        return groovySource;
    }

    public void setGroovySource(String groovySource) {
        this.groovySource = groovySource;
    }

    public long getGroovyUpdatedAt() {
        return groovyUpdatedAt;
    }

    public void setGroovyUpdatedAt(long groovyUpdatedAt) {
        this.groovyUpdatedAt = groovyUpdatedAt;
    }

    public int getBroadcastIndex() {
        return broadcastIndex;
    }

    public void setBroadcastIndex(int broadcastIndex) {
        this.broadcastIndex = broadcastIndex;
    }

    public int getBroadcastTotal() {
        return broadcastTotal;
    }

    public void setBroadcastTotal(int broadcastTotal) {
        this.broadcastTotal = broadcastTotal;
    }
}
