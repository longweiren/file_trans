package com.kopcoder.dataTransfer.excel.engine.typeHandler;

import com.kopcoder.dataTransfer.excel.engine.exception.DataTransException;

public interface TypeHandler {
  Object getValue(String value) throws DataTransException;
}
