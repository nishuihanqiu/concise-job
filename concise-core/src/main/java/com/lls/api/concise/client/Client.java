package com.lls.api.concise.client;

import com.lls.api.concise.core.Result;
import com.lls.api.concise.registry.RegistryConfig;

/************************************
 * JobClient
 * @author liliangshan
 * @date 2018/12/25
 ************************************/
public interface Client {

    Result register(RegistryConfig config);

    Result unregister(RegistryConfig config);



}
