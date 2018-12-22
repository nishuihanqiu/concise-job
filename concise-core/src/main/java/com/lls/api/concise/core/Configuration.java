package com.lls.api.concise.core;

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

  public Configuration(Builder builder) {
    this.address = builder.address;
    this.name = builder.name;
    this.host = builder.host;
    this.port = builder.port;
    this.accessToken = builder.accessToken;
    this.loggingPath = builder.loggingPath;
    this.loggingLiveMills = builder.loggingLiveMills;
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

  public static final class Builder {
    private String address;
    private String name;
    private String host;
    private Integer port;
    private String accessToken;
    private String loggingPath;
    private Long loggingLiveMills;

    public Builder() {
      this.address = null;
      this.name = null;
      this.host = null;
      this.port = null;
      this.accessToken = null;
      this.loggingPath = null;
      this.loggingLiveMills = null;
    }

    public Builder(Configuration configuration) {
      this.address = configuration.getAddress();
      this.name = configuration.getName();
      this.host = configuration.getHost();
      this.port = configuration.getPort();
      this.accessToken = configuration.getAccessToken();
      this.loggingPath = configuration.getLoggingPath();
      this.loggingLiveMills = configuration.getLoggingLiveMills();
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

    public Configuration build() {
      return new Configuration(this);
    }

  }

  public Builder newBuilder() {
    return new Builder(this);
  }
}
