package com.kopcoder.dataTransfer.excel.engine.typeHandler;

import com.kopcoder.dataTransfer.excel.engine.exception.DataTransException;

/**
 * @description 转换String为Boolean类型
 * @author kopcoder
 */
public class BooleanTypeHandler implements TypeHandler {

  public Boolean getValue(String value) throws DataTransException {

    return Boolean.valueOf(value);
  }
}
