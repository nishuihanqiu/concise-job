package com.lls.api.concise.rpc.codec;

import com.lls.api.concise.job.Action;

import java.io.Serializable;

/************************************
 * Response
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class Response implements Serializable {

    private static final long serivalVersionUID = 3839232738484838L;

    private Action action;
    private String error;
    private Object result;

    public boolean isError() {
        return error != null;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
