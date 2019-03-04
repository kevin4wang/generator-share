package com.linlihudong.generator.domain;

public class DoMainProperty {
  // 列名称
  private String columnName;
  // 列注释
  private String columnNote;
  private String jdbcType;
  // 实体类成员变量名称
  private String propertyName;
  private String javaType;
  // 实体类成员变量名称，首字母大写
  private String terProperty;
  // 字符串的最大长度（数据库中的字符串最好设置成字符，不要设置成字节）
  private Integer stringMaxLength;
  // 字段是否可空
  private Boolean nullAble;

  public Integer getStringMaxLength() {
    return stringMaxLength;
  }

  public void setStringMaxLength(Integer stringMaxLength) {
    this.stringMaxLength = stringMaxLength;
  }

  public Boolean getNullAble() {
    return nullAble;
  }

  public void setNullAble(Boolean nullAble) {
    this.nullAble = nullAble;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getColumnNote() {
    return columnNote;
  }

  public void setColumnNote(String columnNote) {
    this.columnNote = columnNote;
  }

  public String getJdbcType() {
    return jdbcType;
  }

  public void setJdbcType(String jdbcType) {
    this.jdbcType = jdbcType;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  public String getJavaType() {
    return javaType;
  }

  public void setJavaType(String javaType) {
    this.javaType = javaType;
  }

  public String getTerProperty() {
    return terProperty;
  }

  public void setTerProperty(String terProperty) {
    this.terProperty = terProperty;
  }

}
