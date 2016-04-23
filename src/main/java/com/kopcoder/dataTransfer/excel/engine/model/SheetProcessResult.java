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
  public void addSuccess(LineProcessSuccessResult success) {
    successResults.add(success);
  }

  /**
   * @Description: 加入解析失败结果
   * @param: fail  解析失败结果
   */
  public void addFailed(LineProcessFailResult fail) {
    failResults.add(fail);
  }

  public void add(LineProcessResult result) {
    //addSuccess
    if(result.isSuccess()) {
      addSuccess((LineProcessSuccessResult) result);
    } else {
      addFailed((LineProcessFailResult) result);
    }
  }

}
