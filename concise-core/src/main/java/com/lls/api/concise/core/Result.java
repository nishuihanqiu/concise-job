package com.lls.api.concise.core;

import com.lls.api.concise.job.Action;

import java.io.Serializable;

/************************************
 * Result
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public class Result implements Serializable {

  public static final long serialVersionUID = 134995993884838949L;

  public static final Result SUCCESS = new Result(Action.SUCCESS, null);
  public static final Result FAILED = new Result(Action.FAILED, null);
  public static final Result LATER = new Result(Action.LATER, null);
  public static final Result FAIL_RETRY = new Result(Action.FAIL_RETRY, null);

  private Action action;
  private String message;
  private Object content;

  public Result() {
  }

  public Result(Action action, String message) {
    this.action = action;
    this.message = message;
  }

  public Result(Object content) {
    this.content = content;
    this.action = Result.SUCCESS.action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public Action getAction() {
    return action;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setContent(Object content) {
    this.content = content;
  }

  public Object getContent() {
    return content;
  }

  @Override
  public String toString() {
    return "Result{" + "action=" + action + ", message='" + message + ", content=" + content + '}';
  }
}
