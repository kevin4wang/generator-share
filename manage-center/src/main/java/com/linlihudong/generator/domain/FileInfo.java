package com.linlihudong.generator.domain;

public class FileInfo {
  private String targetProject;

  private String targetPackage;

  public String getTargetProject() {
    return targetProject;
  }

  public void setTargetProject(String targetProject) {
    this.targetProject = targetProject;
  }

  public String getTargetPackage() {
    return targetPackage;
  }

  public void setTargetPackage(String targetPackage) {
    this.targetPackage = targetPackage;
  }

  @Override
  public String toString() {
    return targetProject + "/" + targetPackage.replaceAll("\\.", "/");
  }

}
