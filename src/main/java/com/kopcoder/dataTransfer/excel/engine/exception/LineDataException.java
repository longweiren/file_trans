package com.kopcoder.dataTransfer.excel.engine.exception;
/**
 * 解析并转换Excel行数据错误时抛出该异常。比如数据格式不对等
 */
public class LineDataException extends Exception{

  public LineDataException() {

  }
  public LineDataException(String message) {
    super(message);
  }
  public LineDataException(Exception e) {
    super(e);
  }
  public LineDataException(String message, Exception e) {
    super(message, e);
  }
}
