package com.kopcoder.dataTransfer.excel.engine.model;

/**
 * @Description 数据行解析失败结果对象
 * @author kopcoder
 */
public class LineProcessFailResult extends LineProcessResult {
  public LineProcessFailResult(LineData lineData, String message) {
    super(lineData, message, false);
  }
}
