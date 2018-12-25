package com.lls.api.concise.rmi;

import com.lls.api.concise.exception.RemotingException;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/************************************
 * AbstractRemoteClient
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public abstract class AbstractRemoteClient implements RemoteClient {

    private static final Logger logger = LoggerFactory.getLogger(RemoteClient.class);

    protected AtomicBoolean started = new AtomicBoolean(false);

    public AbstractRemoteClient() {

    }

    @Override
    final public void start() throws RemotingException {
        try {
            if (started.compareAndSet(false, true)) {
                beforeStart();
                onStart();
                afterStart();
            }
        } catch (Throwable e) {
            logger.error("start failed message:" + e.getMessage(), e);
            throw new RemotingException(e.getMessage(), e);
        }
    }

    @Override
    final public void destroy() {
        try {
            if (started.compareAndSet(true, false)) {
                beforeDestroy();
                onDestroy();
                afterDestroy();
            }
        } catch (Throwable e) {
            logger.error("destroy failed message:" + e.getMessage(), e);
            throw new RemotingException(e.getMessage(), e);
        }
    }

    protected abstract void beforeStart();

    protected abstract void onStart();

    protected abstract void afterStart();

    protected abstract void beforeDestroy();

    protected abstract void onDestroy();

    protected abstract void afterDestroy();

    @Override
    public boolean isStarted() {
        return started.get();
    }

    @Override
    public boolean isDestroy() {
        return !started.get();
    }
}
