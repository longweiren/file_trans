package com.kopcoder.sample.typeHandler;

import org.apache.commons.lang3.StringUtils;

import com.kopcoder.dataTransfer.excel.engine.typeHandler.TypeHandler;
import com.kopcoder.dataTransfer.excel.engine.exception.DataTransException;


public class GenderHandler implements TypeHandler {
  public String getValue(String value) throws DataTransException {
    String v = null;
    if(StringUtils.isNotBlank(value)) {
      switch (value) {
        case "女":
          v = "F";
          break;
        case "男":
          v = "M";
          break;
        default:
          ;
      }
    }

    return v;
  }
}
