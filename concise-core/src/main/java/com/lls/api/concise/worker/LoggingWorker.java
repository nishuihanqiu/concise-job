package com.lls.api.concise.worker;

import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.logging.LoggingAppender;
import com.lls.api.concise.util.FileUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/************************************
 * LoggingWorker
 * @author liliangshan
 * @date 2018/12/25
 ************************************/
public class LoggingWorker implements Worker {

    private static final Logger logger = LoggerFactory.getLogger(LoggingWorker.class);

    private static final long DEFAULT_MIN_RETENTION_TIME_MILLS = 3 * 24 * 3600 * 1000;
    private AtomicBoolean started = new AtomicBoolean(false);
    private Thread cleanThread;
    private volatile long retentionTimeMills = DEFAULT_MIN_RETENTION_TIME_MILLS;
    private Runnable runnable = () -> {
        while (LoggingWorker.this.isStarted()) {
            LoggingWorker.this.cleanLogging();
        }
    };

    private LoggingWorker() {

    }

    public static LoggingWorker getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void start() {
        if (this.retentionTimeMills < DEFAULT_MIN_RETENTION_TIME_MILLS) {
            return;
        }
        if (started.compareAndSet(false, true)) {
            cleanThread = new Thread(runnable);
            cleanThread.setDaemon(true);
            cleanThread.start();
        }
    }

    @Override
    public void destroy() {
        if (started.compareAndSet(true, false)) {
            if (this.cleanThread == null) {
                return;
            }
            cleanThread.interrupt();
            try {
                cleanThread.join();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public boolean isStarted() {
        return started.get();
    }

    @Override
    public boolean isDestroy() {
        return !started.get();
    }

    public void setRetentionTimeMills(long retentionTimeMills) {
        if (retentionTimeMills < DEFAULT_MIN_RETENTION_TIME_MILLS) {
            return;
        }
        this.retentionTimeMills = retentionTimeMills;
    }

    private void cleanLogging() {
        File[] directories = new File(LoggingAppender.getLoggingPath()).listFiles();
        if (directories == null || directories.length == 0) {
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date today = calendar.getTime();
        for (File directory : directories) {
            if (!directory.isDirectory()) continue;
            if (directory.getName().contains("-")) continue;
            Date loggerFileCreatedDate = null;
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                loggerFileCreatedDate = simpleDateFormat.parse(directory.getName());
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
            if (loggerFileCreatedDate == null) {
                continue;
            }

            if ((today.getTime() - loggerFileCreatedDate.getTime()) >= this.retentionTimeMills) {
                FileUtils.deleteRecursively(directory);
            }
        }

    }

    private static class Holder {
        private static final LoggingWorker INSTANCE = new LoggingWorker();
    }
}
