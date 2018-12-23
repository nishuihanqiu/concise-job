package com.lls.api.concise.core;

import com.lls.api.concise.registry.NotifyEventListener;

/************************************
 * Registry
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public interface Registry {

  void registry(RegistryConfig config);

  void unregistry(RegistryConfig config);

  void subscribe(RegistryConfig config, NotifyEventListener listener);

  void unsubscribe(RegistryConfig config, NotifyEventListener listener);

  void destroy();


}
