package com.lls.api.concise.logging;

/************************************
 * LoggerFactory
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public class LoggerFactory {

  public static Logger getLogger(String name) {
    if (name == null) {
      name = "Concise-Job";
    }
    return new ConciseLogger(name);
  }

  public static Logger getLogger(Class<?> clazz) {
    return getLogger(clazz.getName());
  }

}
