package com.lls.api.concise.logging;

import com.lls.api.concise.constant.LoggingConstant;
import com.lls.api.concise.util.FileUtils;
import com.lls.api.concise.util.HelperUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/************************************
 * LoggingAppender
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public class LoggingAppender {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAppender.class);

    public static final InheritableThreadLocal<String> contextHolder = new InheritableThreadLocal<>();
    private static String loggingPath = LoggingConstant.LOGGING_PATH;
    private static String groovyLoggingPath = LoggingConstant.GROOVY_LOGGING_PATH;

    public static void initLoggingPath(String path) {
        if (path != null && path.trim().length() > 0) {
            loggingPath = path;
        }

        File directory = new File(loggingPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        loggingPath = directory.getPath();

        File groovyDirectory = new File(directory, "groovy");
        if (!groovyDirectory.exists()) {
            groovyDirectory.mkdirs();
        }
        groovyLoggingPath = groovyDirectory.getPath();
    }

    public static String getLoggingPath() {
        return loggingPath;
    }

    public static String getGroovyLoggingPath() {
        return groovyLoggingPath;
    }

    public static String generateLoggingFileName(long logId, Date date) {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        File directory = new File(getLoggingPath(), format.format(date));
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory.getPath().concat(File.separator).concat(String.valueOf(logId)).concat(".log");
    }

    public static void appendLogging(String fileName, String content) {
        FileUtils.appendFile(fileName, content);
    }

    public static LoggerDO readLogging(String fileName, int fromLineNumber) {
        if (fileName == null || fileName.trim().length() == 0) {
            return new LoggerDO(fromLineNumber, 0, "read failed, file not found", true);
        }
        File logging = new File(fileName);
        if (!logging.exists()) {
            return new LoggerDO(fromLineNumber, 0, "read failed, file not found", true);
        }

        StringBuilder content = new StringBuilder();
        int toLineNumber = 0;
        LineNumberReader reader = null;

        try {
            reader = new LineNumberReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                toLineNumber = reader.getLineNumber();
                if (toLineNumber >= fromLineNumber) {
                    content.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            HelperUtils.close(reader);
        }

        return new LoggerDO(fromLineNumber, toLineNumber, content.toString(), false);
    }

    public static String readLines(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            HelperUtils.close(reader);
        }
        return null;
    }
}
