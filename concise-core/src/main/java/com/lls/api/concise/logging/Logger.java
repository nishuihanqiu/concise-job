package com.lls.api.concise.logging;


/************************************
 * Logger
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public interface Logger extends org.slf4j.Logger {

  void setEnabledLoggingFile(boolean enabled);

  void writeToFile(String level, String pattern, Object... arguments);

}
