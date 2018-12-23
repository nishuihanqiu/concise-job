package com.lls.api.concise.exception;

/************************************
 * ConciseException
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class ConciseException extends RuntimeException {

    public ConciseException() {
    }

    public ConciseException(String message) {
        super(message);
    }

    public ConciseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConciseException(Throwable cause) {
        super(cause);
    }

}
