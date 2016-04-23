package com.kopcoder.dataTransfer.excel.engine.model;

/**
 * @Description 数据行解析成功结果对象
 * @author kopcoder
 */
public class LineProcessSuccessResult extends LineProcessResult {
  public LineProcessSuccessResult(LineData lineData) {
    super(lineData, null, true);
  }
}
