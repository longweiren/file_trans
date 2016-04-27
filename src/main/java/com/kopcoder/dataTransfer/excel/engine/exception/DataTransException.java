package com.kopcoder.dataTransfer.excel.engine.exception;
/**
 * 解析Excel错误时抛出该异常
 */
public class DataTransException extends Exception {

  public DataTransException() {

  }
  public DataTransException(String message) {
    super(message);
  }
  public DataTransException(String message, Exception e) {
    super(message, e);
  }
}
