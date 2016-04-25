package com.kopcoder.dataTransfer.excel.engine.typeHandler;

import com.kopcoder.dataTransfer.excel.engine.exception.DataTransException;
import com.kopcoder.dataTransfer.excel.engine.typeHandler.TypeHandler;

public final class NonTypeHandler implements TypeHandler {
  public Object getValue(String value) throws DataTransException {
    return null;
  }
}
