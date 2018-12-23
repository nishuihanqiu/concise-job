package com.lls.api.concise.job;

/************************************
 * Action
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public enum Action {

  SUCCESS(200, "ACTION_SUCCESS"),    // 执行成功,这种情况 直接反馈客户端
  FAILED(400, "ACTION_FAILED"),     // 执行失败,这种情况,直接反馈给客户端,不重新执行
  LATER(501, "ACTION_LATER"),       // 稍后重新执行,这种情况, 不反馈客户端,稍后重新执行,不过有最大重试次数
  FAIL_RETRY(502, "ACTION_FAIL_RETRY");   // 执行异常, 这中情况也会重试

  private int status;
  private String description;

  Action(int status, String description) {
    this.status = status;
    this.description = description;
  }

  public int getStatus() {
    return status;
  }

  public String getDescription() {
    return description;
  }

}
