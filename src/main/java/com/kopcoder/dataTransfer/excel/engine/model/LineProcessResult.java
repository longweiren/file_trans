package com.kopcoder.dataTransfer.excel.engine.model;

/**
 * @Description: 数据行解析结果对象
 * @author: kopcoder
 */
public class LineProcessResult {

  // 解析后的行数据
  private LineData lineData;
  // 解析结果信息（失败后的提示信息）
  private String msg;
  // 是否解析成功：true成功， false失败
  private boolean success;

  public LineProcessResult(LineData lineData, String msg, boolean success) {
    super();
    this.lineData = lineData;
    this.msg = msg;
    this.success = success;
  }

  public LineData getLineData() {
    return lineData;
  }
}
