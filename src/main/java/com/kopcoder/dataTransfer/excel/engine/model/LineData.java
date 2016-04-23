package com.kopcoder.dataTransfer.excel.engine.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

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
  public abstract LineData wrapperWith(JSONObject jsonObj);

  /**
   * 根据单元格名称查找领域对象对应的field
   */
  private Field getMappedField(String cellName) {

    Field[] fields = getClass().getDeclaredFields();

    if(fields != null) {
      for (Field field : fields) {
        CellField cellDef = field.getAnnotation(CellField.class);

        if( cellDef != null) {
          if( StringUtils.isNotBlank(cellDef.cellName())
            && cellDef.cellName().equals(cellName)) {
            return field;
          }
        }
      }
    }

    return null;
  }

  private Method getStringSetter(Field field) {
    String fieldName = field.getName();
    String setterName = "set" + Character.toUpperCase(fieldName.charAt(0))
      + fieldName.substring(1);

    try {
      return getClass().getMethod(setterName, String.class);
    } catch (NoSuchMethodException e) {
      logger.error("获取setter方法异常：" + e.getMessage());
      return null;
    }

  }

  /**
   * @Description 把单元格数据设置到领域对象对应的字段上
   */
  public final void setCellValue(String cellName, String value) {
    if(cellName == null || value == null) {
      return;
    }

    Field targetField = getMappedField(cellName);

    setFieldValue(targetField, value);
  }

  /**
   * @description 为对象指定属性赋值
   * @param field 待赋值属性
   * @param vlaue 赋值内容
   * todo process multi type value
   */
  private void setFieldValue(Field field, String value) {
    if(field == null) {
      return ;
    }

    // use field.set
    try {
      field.set(this, value);
      return;
    } catch (IllegalArgumentException e) {
      logger.debug("set field value warning:" + e.getMessage());
    } catch (IllegalAccessException e) {
      logger.debug("set field value warning:" + e.getMessage());
    }

    // use setX method
    Method setMethod = getStringSetter(field);
    if(setMethod != null) {
      try {
        setMethod.invoke(this, value);
      } catch (SecurityException e) {
        logger.error("setter invoke error:" + e.getMessage());
      } catch ( IllegalArgumentException e) {
        logger.error("setter invoke error:" + e.getMessage());
      } catch (IllegalAccessException e) {
        logger.error("setter invoke error:" + e.getMessage());
      } catch (InvocationTargetException e) {
        logger.error("setter invoke error:" + e.getMessage());
      }
    }
  }

  public LineProcessResult successResult() {
    return new LineProcessSuccessResult(this);
  }

  public LineProcessResult failedResult(String msg) {
    return new LineProcessFailResult(this, msg);
  }
}
