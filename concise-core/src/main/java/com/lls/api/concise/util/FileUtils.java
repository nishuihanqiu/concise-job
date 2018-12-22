package com.lls.api.concise.util;

import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/************************************
 * FileUtils
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public class FileUtils {

  private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);


  public static boolean deleteRecursively(File root) {
    if (root == null || !root.exists()) {
      return false;
    }

    if (root.isDirectory()) {
      File[] children = root.listFiles();
      if (children != null && children.length != 0) {
        Arrays.stream(children).forEach(FileUtils::deleteRecursively);
      }
    }
    return root.delete();
  }

  public static void deleteFile(String fileName) {
    File file = new File(fileName);
    if (file.exists()) {
      file.delete();
    }
  }

  public static void appendFile(String fileName, String content) {
    File file = new File(fileName);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
        return;
      }
    }

    if (content == null) {
      content = "";
    }
    content += "\r\n";

    FileOutputStream fileOutputStream = null;
    try {
      fileOutputStream = new FileOutputStream(file, true);
      fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
      fileOutputStream.flush();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    } finally {
      HelperUtils.close(fileOutputStream);
    }
  }

  public static List<String> getFileLines(String fileName) {
    List<String> results = new ArrayList<>();
    File file = new File(fileName);
    if (!file.exists()) {
      return results;
    }

    LineNumberReader reader = null;
    try {
      reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.trim().length() > 0) {
          results.add(line);
        }
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    } finally {
      HelperUtils.close(reader);
    }

    return results;
  }

}
