package com.lls.api.concise.rpc.codec;

import java.io.Serializable;
import java.util.Arrays;

/************************************
 * Request
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class Request implements Serializable {

    private static final long serialVersionUID = 1223488488888L;

    private String serverAddress;
    private long createdAt;
    private String accessToken;
    private String className;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] parameterTypes;

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    @Override
    public String toString() {
        return "Request{" +
            "serverAddress='" + serverAddress + '\'' +
            ", createdAt=" + createdAt +
            ", accessToken='" + accessToken + '\'' +
            ", className='" + className + '\'' +
            ", methodName='" + methodName + '\'' +
            ", parameters=" + Arrays.toString(parameters) +
            ", parameterTypes=" + Arrays.toString(parameterTypes) +
            '}';
    }
}
