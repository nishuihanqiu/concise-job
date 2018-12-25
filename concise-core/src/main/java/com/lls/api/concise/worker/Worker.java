package com.lls.api.concise.worker;

/************************************
 * Worker
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public interface Worker {

  void start();

  void destroy();

  boolean isStarted();

  boolean isDestroy();

}
