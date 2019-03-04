package com.linlihudong.generator.domain;


public class ConnectInfo {
  private String classPath;
  private String driverClass;
  private String url;
  private String userName;
  private String password;


  public String getClassPath() {
    return classPath;
  }

  public void setClassPath(String classPath) {
    this.classPath = classPath;
  }

  public String getDriverClass() {
    return driverClass;
  }

  public void setDriverClass(String driverClass) {
    this.driverClass = driverClass;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
