package com.kopcoder.dataTransfer.excel.engine.typeHandler;

import com.kopcoder.dataTransfer.excel.engine.exception.DataTransException;

/**
 * @description 转换String为Integer类型
 * @author kopcoder
 */
public class IntegerTypeHandler implements TypeHandler {

  public Integer getValue(String value) throws DataTransException {
    try {
      return Integer.valueOf(value);
    } catch (NumberFormatException nfe) {
      throw new DataTransException("数据转换异常. 不能把[" + value + "]转换成Integer", nfe);
    }

  }
}
