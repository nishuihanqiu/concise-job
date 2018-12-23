package com.lls.api.concise.exception;

/************************************
 * RemotingException
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class RemotingException extends RuntimeException {

    private static final long serialVersionUID = -5690687334570505110L;

    public RemotingException(String message) {
        super(message);
    }

    public RemotingException(String message, Throwable cause) {
        super(message, cause);
    }
}
