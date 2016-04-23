package com.kopcoder.dataTransfer.excel.engine.handler;

import com.kopcoder.dataTransfer.excel.engine.model.LineData;

public interface LineDataHandler<T extends LineData> {

  /**
   * @description 业务处理数据行记录
   * @param lineData 数据行记录
   */
  void handle(T lineData);
}
