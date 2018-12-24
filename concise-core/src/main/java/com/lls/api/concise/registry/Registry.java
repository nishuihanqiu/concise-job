package com.lls.api.concise.registry;

/************************************
 * Registry
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public interface Registry {

  void register(RegistryConfig config);

  void unregister(RegistryConfig config);

  void subscribe(RegistryConfig config, NotifyEventListener listener);

  void unsubscribe(RegistryConfig config, NotifyEventListener listener);

  void destroy();


}
