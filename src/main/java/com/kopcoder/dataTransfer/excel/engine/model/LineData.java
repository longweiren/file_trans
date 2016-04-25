package com.kopcoder.dataTransfer.excel.engine.model;

import com.kopcoder.dataTransfer.excel.engine.utils.BeanUtils;
import com.kopcoder.dataTransfer.excel.engine.exception.DataTransException;
import com.kopcoder.dataTransfer.excel.engine.exception.LineDataException;


import java.lang.reflect.Field;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.lang3.StringUtils;

import com.kopcoder.dataTransfer.excel.engine.typeHandler.TypeHandler;
import com.kopcoder.dataTransfer.excel.engine.typeHandler.TypeHandlerFactory;

import com.kopcoder.dataTransfer.excel.engine.annotation.CellField;
import com.kopcoder.dataTransfer.excel.engine.model.LineProcessResult;

/**
 * @Description: 数据行记录
 * @author: kopcoder
 */
public abstract class LineData {

  private Logger logger = LogManager.getLogger(getClass());

  /**
   * @Description: 获取lineData数据记录的标识(缓存数据时作为键值)
   * @return
   */
  public abstract String getIdentify();

  /**
   * @Description: json对象转lineData对象
   * @return
   */
  // public abstract LineData wrapperWith(JSONObject jsonObj);

  /**
   * @Description 把单元格数据设置到领域对象对应的字段上
   */
  public final void setCellValue(String cellName, String value) throws LineDataException {
    if(cellName == null || value == null) {
      return;
    }

    Field targetField = BeanUtils.getMappedField(getClass(), cellName);
    TypeHandler typeHandler = BeanUtils.getTypeHandler(targetField);
    if(typeHandler == null) {
      typeHandler = TypeHandlerFactory.get(targetField.getType());
    }

    Object fieldValue = value;
    try {
      if(typeHandler != null) {
        fieldValue = typeHandler.getValue(value);
      }
    } catch (DataTransException e) {
      throw new LineDataException(e);
    }
    BeanUtils.setFieldValue(this, targetField, fieldValue);
  }


  public LineProcessResult successResult() {
    return new LineProcessSuccessResult(this);
  }

  public LineProcessResult failedResult(String msg) {
    return new LineProcessFailResult(this, msg);
  }
}
