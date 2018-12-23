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
  private Integer jobId;
  private Integer priority = 0;
  private Map<String, String> extParams;
  private boolean feedback;
  private int maxRetryTimes = 0;
  private String cronExpression;
  private int repeatCount = 0;
  private long repeatInterval = 0;
  private Long triggerTime;
  private String blockStrategy;



}
