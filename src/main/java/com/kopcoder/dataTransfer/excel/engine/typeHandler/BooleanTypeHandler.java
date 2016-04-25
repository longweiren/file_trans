package com.kopcoder.dataTransfer.excel.engine.typeHandler;

import com.kopcoder.dataTransfer.excel.engine.exception.DataTransException;

public class BooleanTypeHandler implements TypeHandler {

  public Boolean getValue(String value) throws DataTransException {

    return Boolean.valueOf(value);
  }
}
