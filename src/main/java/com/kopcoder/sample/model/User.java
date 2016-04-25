package com.kopcoder.sample.model;

import com.kopcoder.dataTransfer.excel.engine.model.LineData;

import com.kopcoder.dataTransfer.excel.engine.annotation.CellField;

import com.kopcoder.sample.typeHandler.GenderHandler;

public class User extends LineData {

  private String id;
  @CellField(cellName="Name")
  private String username;
  @CellField(cellName="Pass")
  private String password;
  @CellField(cellName="age")
  private Integer age;
  @CellField(cellName="is_robust")
  private Boolean isRobust;
  @CellField(cellName="gender", typeHandler=GenderHandler.class)
  private String gender;

  public void setId(String id){
    this.id = id;
  }
  public String getId() {
    return this.id;
  }

  public void setUsername(String username){
    this.username = username;
  }
  public String getUsername() {
    return this.username;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  public String getPassword() {
    return this.password;
  }

  public void setAge(Integer age) {
    this.age = age;
  }
  public Integer getAge() {
    return this.age;
  }

  public void setIsRobust(Boolean isRobust) {
    this.isRobust = isRobust;
  }
  public Boolean getIsRobust() {
    return this.isRobust;
  }

  public String getIdentify() {
    return this.username;
  }


  public String toString() {
    return "[User] id = " + this.id + ", username=" + this.username + ", password=" + this.password + ", age=" + this.age + ", isRobust=" + this.isRobust;
  }
}
