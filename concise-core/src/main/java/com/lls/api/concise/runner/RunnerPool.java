package com.lls.api.concise.runner;

import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

/************************************
 * RunnerPool
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class RunnerPool {

  private static final Logger logger = LoggerFactory.getLogger(RunnerPool.class);

  private ThreadPoolExecutor threadPoolExecutor = null;
}
