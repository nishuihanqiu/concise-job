package com.lls.api.concise.rpc;

import com.lls.api.concise.exception.RemotingException;

/************************************
 * RemoteServer
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public interface RemoteServer {

    void start() throws RemotingException;

    void destroy();

    boolean isStarted();

    boolean isDestroy();

}
