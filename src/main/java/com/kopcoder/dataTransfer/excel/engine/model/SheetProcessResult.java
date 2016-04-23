package com.kopcoder.dataTransfer.excel.engine.model;

import java.util.List;
import java.util.ArrayList;

/**
 * @Description: Sheet解析结果集合
 * @author: kopcoder
 */
public class SheetProcessResult {

  // 解析成功数据集合
  private List<LineProcessResult> successResults = new ArrayList<LineProcessResult>();
  // 解析失败数据集合
  private List<LineProcessResult> failResults = new ArrayList<LineProcessResult>();

  public List<LineProcessResult> getSuccessResults() {
    return successResults;
  }
  public List<LineProcessResult> getFailResults() {
    return failResults;
  }

  /**
   * @Description: 加入解析成功结果
   * @param: success  解析成功结果
   */
  public void addSuccess(LineProcessResult success) {
    successResults.add(success);
  }

  /**
   * @Description: 加入解析失败结果
   * @param: fail  解析失败结果
   */
  public void addFailed(LineProcessResult fail) {
    failResults.add(fail);
  }

  /**
   * @Description: 合并解析结果集到当前结果集
   * @param: sheetResults 待加入的解析结果集
   */
  public void addAll(LineProcessResult sheetResults) {
    if(sheetResults != null && sheetResults.successResults != null) {
      successResults.addAll(sheetResults.successResults);
    }

    if(sheetResults != null && sheetResults.failResults != null) {
      failResults.addAll(sheetResults.failResults);
    }
  }
}
