package com.lls.api.concise.runner;

import com.lls.api.concise.job.JobContext;
import com.lls.api.concise.core.Result;

/************************************
 * Runner
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public interface Runner {

  Result execute(JobContext jobContext) throws Throwable;

}
