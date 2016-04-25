package com.kopcoder.dataTransfer.excel.engine.exception;

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
