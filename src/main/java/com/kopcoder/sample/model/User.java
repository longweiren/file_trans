package com.kopcoder.sample.model;

import com.kopcoder.dataTransfer.excel.engine.model.LineData;

import com.kopcoder.dataTransfer.excel.engine.annotation.CellField;

public class User extends LineData {

  @CellField(cellName="Name")
  private String username;
  @CellField(cellName="Pass")
  private String password;

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

  public String getIdentify() {
    return this.username;
  }

  public String toString() {
    return "[User] username=" + this.username
      + ", password=" + this.password;
  }
}
