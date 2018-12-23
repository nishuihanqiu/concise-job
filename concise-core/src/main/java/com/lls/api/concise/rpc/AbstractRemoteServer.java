package com.lls.api.concise.rpc;

import com.lls.api.concise.exception.RemotingException;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/************************************
 * AbstractRemoteServer
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public abstract class AbstractRemoteServer implements RemoteServer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractRemoteServer.class);

    protected AtomicBoolean started = new AtomicBoolean(false);

    public AbstractRemoteServer() {

    }

    @Override
    final public void start() throws RemotingException {
        try {
            if (started.compareAndSet(false, true)) {
                beforeServerStart();
                onServerStart();
                afterServerStart();
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
                beforeServerDestroy();
                onServerDestroy();
                afterServerDestroy();
            }
        } catch (Throwable e) {
            logger.error("destroy failed message:" + e.getMessage(), e);
            throw new RemotingException(e.getMessage(), e);
        }
    }

    protected abstract void beforeServerStart();

    protected abstract void onServerStart();

    protected abstract void afterServerStart();

    protected abstract void beforeServerDestroy();

    protected abstract void onServerDestroy();

    protected abstract void afterServerDestroy();

    @Override
    public boolean isStarted() {
        return started.get();
    }

    @Override
    public boolean isDestroy() {
        return !started.get();
    }

}
