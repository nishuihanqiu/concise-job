package com.lls.api.concise.core;

import java.util.HashMap;
import java.util.Map;

/************************************
 * Configuration
 * @author liliangshan
 * @date 2018/12/22
 ************************************/
public class Configuration {

    private String address;
    private String name;
    private String host;
    private int port;
    private String accessToken;
    private String loggingPath;
    private long loggingLiveMills;
    private long maxRequestIntervalTime;
    private long retryPeriod;
    private int serializationVersion;
    private final Map<String, String> parameters = new HashMap<>();

    public Configuration(Builder builder) {
        this.address = builder.address;
        this.name = builder.name;
        this.host = builder.host;
        this.port = builder.port;
        this.accessToken = builder.accessToken;
        this.loggingPath = builder.loggingPath;
        this.loggingLiveMills = builder.loggingLiveMills;
        this.maxRequestIntervalTime = builder.maxRequestIntervalTime;
        this.retryPeriod = builder.retryPeriod;
        this.serializationVersion = builder.serializationVersion;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getLoggingPath() {
        return loggingPath;
    }

    public void setLoggingPath(String loggingPath) {
        this.loggingPath = loggingPath;
    }

    public long getLoggingLiveMills() {
        return loggingLiveMills;
    }

    public void setLoggingLiveMills(long loggingLiveMills) {
        this.loggingLiveMills = loggingLiveMills;
    }

    public void setMaxRequestIntervalTime(long maxRequestIntervalTime) {
        this.maxRequestIntervalTime = maxRequestIntervalTime;
    }

    public long getMaxRequestIntervalTime() {
        return maxRequestIntervalTime;
    }

    public void setRetryPeriod(long retryPeriod) {
        this.retryPeriod = retryPeriod;
    }

    public long getRetryPeriod() {
        return retryPeriod;
    }

    public void setSerializationVersion(int serializationVersion) {
        this.serializationVersion = serializationVersion;
    }

    public int getSerializationVersion() {
        return serializationVersion;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void putParameter(String key, String value) {
        parameters.put(key, value);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getParameter(String key, String defaultValue) {
        String value = parameters.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static final class Builder {

        private static final long DEFAULT_REQUEST_MAX_TIME = 180000;
        private static final long DEFAULT_RETRY_MILLS_TIME = 5000;

        private String address;
        private String name;
        private String host;
        private Integer port;
        private String accessToken;
        private String loggingPath;
        private Long loggingLiveMills;
        private Long maxRequestIntervalTime;
        private Long retryPeriod;
        private Integer serializationVersion;

        public Builder() {
            this.address = null;
            this.name = null;
            this.host = null;
            this.port = null;
            this.accessToken = null;
            this.loggingPath = null;
            this.loggingLiveMills = null;
            this.maxRequestIntervalTime = DEFAULT_REQUEST_MAX_TIME;
            this.retryPeriod = DEFAULT_RETRY_MILLS_TIME;
            this.serializationVersion = 0;
        }

        public Builder(Configuration configuration) {
            this.address = configuration.getAddress();
            this.name = configuration.getName();
            this.host = configuration.getHost();
            this.port = configuration.getPort();
            this.accessToken = configuration.getAccessToken();
            this.loggingPath = configuration.getLoggingPath();
            this.loggingLiveMills = configuration.getLoggingLiveMills();
            this.maxRequestIntervalTime = configuration.getMaxRequestIntervalTime();
            this.retryPeriod = configuration.getRetryPeriod();
            this.serializationVersion = configuration.getSerializationVersion();
        }

        public Builder buildAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder buildName(String name) {
            this.name = name;
            return this;
        }

        public Builder buildHost(String host) {
            this.host = host;
            return this;
        }

        public Builder buildPort(Integer port) {
            this.port = port;
            return this;
        }

        public Builder buildAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder buildLoggingPath(String loggingPath) {
            this.loggingPath = loggingPath;
            return this;
        }

        public Builder buildLoggingLiveMills(long loggingLiveMills) {
            this.loggingLiveMills = loggingLiveMills;
            return this;
        }

        public Builder buildMaxRequestIntervalTime(long maxRequestIntervalTime) {
            this.maxRequestIntervalTime = maxRequestIntervalTime;
            return this;
        }

        public Builder buildRetryPeriod(long retryPeriod) {
            this.retryPeriod = retryPeriod;
            return this;
        }

        public Builder buildSerializationVersion(int version) {
            this.serializationVersion = version;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }

    }

    public Builder newBuilder() {
        return new Builder(this);
    }
}
