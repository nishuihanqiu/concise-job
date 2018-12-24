package com.lls.api.concise.registry;

import com.lls.api.concise.core.Context;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/************************************
 * AbstractRegistry
 * @author liliangshan
 * @date 2018/12/24
 ************************************/
public abstract class AbstractRegistry implements Registry {

    private static final Logger logger = LoggerFactory.getLogger(Registry.class);
    private final Set<RegistryConfig> registered = ConcurrentHashMap.newKeySet();
    private final ConcurrentMap<RegistryConfig, Set<NotifyEventListener>> subscribed = new ConcurrentHashMap<>();
    protected Context context;

    public AbstractRegistry(Context context) {
        this.context = context;
    }

    @Override
    public void register(RegistryConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("register config is null.");
        }
        if (logger.isInfoEnabled()) {
            logger.info("register config:{}", config);
        }
        registered.add(config);
    }

    @Override
    public void unregister(RegistryConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("unregister config is null.");
        }
        if (logger.isInfoEnabled()) {
            logger.info("unregister config:{}", config);
        }
        registered.remove(config);
    }

    @Override
    public void subscribe(RegistryConfig config, NotifyEventListener listener) {
        if (config == null) {
            throw new IllegalArgumentException("subscribe config is null.");
        }
        if (listener == null) {
            throw new IllegalArgumentException("subscribe listener is null.");
        }

        if (logger.isInfoEnabled()) {
            logger.info("subscribe config:{}", config);
        }
        Set<NotifyEventListener> listeners = subscribed.get(config);
        if (listeners == null) {
            subscribed.putIfAbsent(config, ConcurrentHashMap.newKeySet());
            listeners = subscribed.get(config);
        }
        listeners.add(listener);
    }

    @Override
    public void unsubscribe(RegistryConfig config, NotifyEventListener listener) {
        if (config == null) {
            throw new IllegalArgumentException("unsubscribe config is null.");
        }
        if (listener == null) {
            throw new IllegalArgumentException("unsubscribe listener is null.");
        }

        if (logger.isInfoEnabled()) {
            logger.info("unsubscribe config:{}", config);
        }

        Set<NotifyEventListener> listeners = subscribed.get(config);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    @Override
    public void destroy() {
        if (logger.isInfoEnabled()) {
            logger.info("destroy registry");
        }
        Set<RegistryConfig> destroyRegistered = new HashSet<>(getRegistered());
        if (!destroyRegistered.isEmpty()) {
            for (RegistryConfig registryConfig : new HashSet<>(getRegistered())) {
                try {
                    unregister(registryConfig);
                    if (logger.isInfoEnabled()) {
                        logger.info("destroy unregister config: {}", registryConfig);
                    }
                } catch (Throwable t) {
                    logger.warn("failed to unregister config:{} on destroy, cause:{} {}", registryConfig, t.getMessage(), t);
                }
            }
        }
        Map<RegistryConfig, Set<NotifyEventListener>> destroySubscribed = new HashMap<>(getSubscribed());
        if (!destroySubscribed.isEmpty()) {
            for (Map.Entry<RegistryConfig, Set<NotifyEventListener>> entry : destroySubscribed.entrySet()) {
                RegistryConfig registryConfig = entry.getKey();
                for (NotifyEventListener listener : entry.getValue()) {
                    try {
                        unsubscribe(registryConfig, listener);
                        if (logger.isInfoEnabled()) {
                            logger.info("destroy unsubscribe config:{}", registryConfig);
                        }
                    } catch (Throwable t) {
                        logger.warn("Failed to unsubscribe registryConfig:{}  on destroy, cause:{},{} ", registryConfig, t.getMessage(), t);
                    }
                }
            }
        }
    }

    protected Set<RegistryConfig> getRegistered() {
        return registered;
    }

    protected ConcurrentMap<RegistryConfig, Set<NotifyEventListener>> getSubscribed() {
        return subscribed;
    }

    protected void recover() {
        Set<RegistryConfig> recoverRegistered = new HashSet<>(getRegistered());
        if (!recoverRegistered.isEmpty()) {
            if (logger.isInfoEnabled()) {
                logger.info("recover register config " + recoverRegistered);
            }
            for (RegistryConfig registryConfig : recoverRegistered) {
                register(registryConfig);
            }
        }

        Map<RegistryConfig, Set<NotifyEventListener>> recoverSubscribed = new HashMap<>(getSubscribed());
        if (!recoverRegistered.isEmpty()) {
            if (logger.isInfoEnabled()) {
                logger.info("recover subscribe config " + recoverRegistered);
            }
            for (Map.Entry<RegistryConfig, Set<NotifyEventListener>> entry : recoverSubscribed.entrySet()) {
                RegistryConfig registryConfig = entry.getKey();
                for (NotifyEventListener listener : entry.getValue()) {
                    subscribe(registryConfig, listener);
                }
            }
        }
    }
}
