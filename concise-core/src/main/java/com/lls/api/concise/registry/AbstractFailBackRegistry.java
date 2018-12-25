package com.lls.api.concise.registry;

import com.lls.api.concise.core.Context;
import com.lls.api.concise.core.NamedThreadFactory;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/************************************
 * AbstractFailBackRegistry
 * @author liliangshan
 * @date 2018/12/24
 ************************************/
public abstract class AbstractFailBackRegistry extends AbstractRegistry {

    private static final Logger logger = LoggerFactory.getLogger(AbstractFailBackRegistry.class);

    // 定时任务执行器
    private final ScheduledExecutorService retryExecutor = Executors.newScheduledThreadPool(1,
        new NamedThreadFactory("Concise-FailBack-Registry", true));

    // 失败重试定时器，定时检查是否有请求失败，如有，无限次重试
    private ScheduledFuture<?> retryFuture;

    // 注册失败的定时重试
    private final Set<RegistryConfig> failedRegistereds = ConcurrentHashMap.newKeySet();
    private final Set<RegistryConfig> failedUnregistereds = ConcurrentHashMap.newKeySet();
    private final ConcurrentMap<RegistryConfig, Set<NotifyEventListener>> failedSubscribeds = new ConcurrentHashMap<>();
    private final ConcurrentMap<RegistryConfig, Set<NotifyEventListener>> failedUnsubscribeds = new ConcurrentHashMap<>();
    private final ConcurrentMap<RegistryConfig, Map<NotifyEventListener, Pair>> failedNotifieds = new ConcurrentHashMap<>();


    public AbstractFailBackRegistry(Context context) {
        super(context);
        long retryPeriod = context.getConfiguration().getRetryPeriod();
        this.retryFuture = this.retryExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                // 检测并连接注册中心
                try {
                    retry();
                } catch (Throwable t) { // 防御性容错
                    logger.error("Unexpected error occur at failed retry, cause: " + t.getMessage(), t);
                }
            }
        }, retryPeriod, retryPeriod, TimeUnit.MICROSECONDS);
    }

    private void retry() {

    }

    private class Pair {
        NotifyEvent event;
        List<RegistryConfig> configs;

        public Pair(NotifyEvent event, List<RegistryConfig> configs) {
            this.event = event;
            this.configs = configs;
        }
    }
}
