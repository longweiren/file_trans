package com.kopcoder.dataTransfer.excel.engine.validator;

import com.kopcoder.dataTransfer.excel.engine.model.LineData;
import com.kopcoder.dataTransfer.excel.engine.exception.LineDataException;

/**
 * @description Excel行数据验证器
 * @author kopcoder
 */
public interface LineDataValidator<T extends LineData> {
  /**
   * @description   验证航数据是否符合要求
   * @param lineData 待验证的行数据
   * @throws LineDataException 行数据不符合要求抛出该异常
   */
  void validate(T lineData) throws LineDataException;
}
