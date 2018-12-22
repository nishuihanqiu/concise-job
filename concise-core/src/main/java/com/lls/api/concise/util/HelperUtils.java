package com.lls.api.concise.util;

import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/************************************
 * HelperUtils
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public class HelperUtils {

  private static final Logger logger = LoggerFactory.getLogger(HelperUtils.class);

  public static void close(Closeable closeable) {
    if (closeable == null) {
      return;
    }

    try {
      closeable.close();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

}
