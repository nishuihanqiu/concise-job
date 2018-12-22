package com.lls.api.concise.logging;

import java.io.Serializable;

/************************************
 * LoggerDO
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public class LoggerDO implements Serializable {

  private static final long serialVersionUID = 82938877787788L;

  private int headLineNumber;
  private int tailLineNumber;
  private String content;
  private boolean isEnd;

  public LoggerDO(int headLineNumber, int tailLineNumber, String content, boolean isEnd) {
    this.headLineNumber = headLineNumber;
    this.tailLineNumber = tailLineNumber;
    this.content = content;
    this.isEnd = isEnd;
  }

  public int getHeadLineNumber() {
    return headLineNumber;
  }

  public void setHeadLineNumber(int headLineNumber) {
    this.headLineNumber = headLineNumber;
  }

  public int getTailLineNumber() {
    return tailLineNumber;
  }

  public void setTailLineNumber(int tailLineNumber) {
    this.tailLineNumber = tailLineNumber;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isEnd() {
    return isEnd;
  }

  public void setEnd(boolean end) {
    isEnd = end;
  }

  @Override
  public String toString() {
    return "LoggerDO{" +
      "headLineNumber=" + headLineNumber +
      ", tailLineNumber=" + tailLineNumber +
      ", content='" + content + '\'' +
      ", isEnd=" + isEnd +
      '}';
  }

}
