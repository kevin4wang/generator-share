package com.linlihudong.generator.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DoMain {
  private Boolean oracle;
  // 表名
  private String tableName;
  private String packageName;
  private String simplePackageName;
  private String daoPackageName;
  private String mapperPackageName;
  private String servicePackageName;
  private String controllerPackageName;
  // 实体类 类名
  private String className;
  // className 首字母小写
  private String nickName;
  // 表注释
  private String classNote;
  // 主键类型
  private String idJavaType;
  // 作者
  private String author = System.getProperty("user.name");
  // 创建日期
  private String date = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date());

  // 入参
  private String inBeanPackageName;
  // 出参
  private String outBeanPackageName;

  public String getInBeanPackageName() {
    return inBeanPackageName;
  }

  public void setInBeanPackageName(String inBeanPackageName) {
    this.inBeanPackageName = inBeanPackageName;
  }

  public String getOutBeanPackageName() {
    return outBeanPackageName;
  }

  public void setOutBeanPackageName(String outBeanPackageName) {
    this.outBeanPackageName = outBeanPackageName;
  }

  private List<DoMainProperty> doMainProperties;

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getIdJavaType() {
    return idJavaType;
  }

  public void setIdJavaType(String idJavaType) {
    this.idJavaType = idJavaType;
  }

  public String getSimplePackageName() {
    return simplePackageName;
  }

  public void setSimplePackageName(String simplePackageName) {
    this.simplePackageName = simplePackageName;
  }

  public String getControllerPackageName() {
    return controllerPackageName;
  }

  public void setControllerPackageName(String controllerPackageName) {
    this.controllerPackageName = controllerPackageName;
  }

  public Boolean getOracle() {
    return oracle;
  }

  public void setOracle(Boolean oracle) {
    this.oracle = oracle;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getDaoPackageName() {
    return daoPackageName;
  }

  public void setDaoPackageName(String daoPackageName) {
    this.daoPackageName = daoPackageName;
  }

  public String getMapperPackageName() {
    return mapperPackageName;
  }

  public void setMapperPackageName(String mapperPackageName) {
    this.mapperPackageName = mapperPackageName;
  }

  public String getServicePackageName() {
    return servicePackageName;
  }

  public void setServicePackageName(String servicePackageName) {
    this.servicePackageName = servicePackageName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getClassNote() {
    return classNote;
  }

  public void setClassNote(String classNote) {
    this.classNote = classNote;
  }

  public List<DoMainProperty> getDoMainProperties() {
    return doMainProperties;
  }

  public void setDoMainProperties(List<DoMainProperty> doMainProperties) {
    this.doMainProperties = doMainProperties;
  }
}
