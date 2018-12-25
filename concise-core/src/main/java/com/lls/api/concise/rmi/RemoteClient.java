package com.lls.api.concise.rmi;

import com.lls.api.concise.exception.RemotingException;
import com.lls.api.concise.rmi.codec.Request;
import com.lls.api.concise.rmi.codec.Response;

/************************************
 * RemoteClient
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public interface RemoteClient {

    void start() throws RemotingException;

    boolean isStarted();

    Response send(Request request);

    void destroy();

    boolean isDestroy();

}
