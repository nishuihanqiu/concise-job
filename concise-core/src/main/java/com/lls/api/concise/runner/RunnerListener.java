package com.lls.api.concise.runner;

import com.lls.api.concise.rpc.codec.Response;

/************************************
 * RunnerListener
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public interface RunnerListener {

  void onCompleted(Response response);

  void onFailed(Response response, Throwable t);

}
