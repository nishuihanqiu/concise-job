package com.lls.api.concise.exception;

/************************************
 * NoAvailableRunnerException
 * @author liliangshan
 * @date 2018/12/24
 ************************************/
public class NoAvailableRunnerException extends Exception{

    private static final long serialVersionUID = 5317008601154858522L;

    public NoAvailableRunnerException() {
        super();
    }

    public NoAvailableRunnerException(String message) {
        super(message);
    }

    public NoAvailableRunnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAvailableRunnerException(Throwable cause) {
        super(cause);
    }


}
