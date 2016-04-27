package com.kopcoder.dataTransfer.excel.engine.typeHandler;

import com.kopcoder.dataTransfer.excel.engine.exception.DataTransException;

/**
 * @description 转换String类型值为业务对象属性需要的类型的值
 * @author kopcoder
 */
public interface TypeHandler {
  Object getValue(String value) throws DataTransException;
}
