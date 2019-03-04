package com.linlihudong.generator.domain;

public enum GeneratorOracle {
  VARCHAR2("VARCHAR2", "String"), NVARCHAR("NVARCHAR", "String"), INTEGER("NUMBER", "INTEGER", 9),
  // 3、30~39 的长度，比例为0
  FLOAT("NUMBER", "FLOAT", 18),;

  private String dbType;

  private String javaName;

  private Integer length;



  GeneratorOracle(String dbType, String javaName, Integer length) {
    this.dbType = dbType;
    this.javaName = javaName;
    this.length = length;
  }

  GeneratorOracle(String dbType, String javaName) {
    this.dbType = dbType;
    this.javaName = javaName;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public String getDbType() {
    return dbType;
  }

  public void setDbType(String dbType) {
    this.dbType = dbType;
  }

  public String getJavaName() {
    return javaName;
  }

  public void setJavaName(String javaName) {
    this.javaName = javaName;
  }
}
