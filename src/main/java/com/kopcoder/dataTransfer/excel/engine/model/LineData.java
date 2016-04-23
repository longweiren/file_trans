package com.kopcoder.dataTransfer.excel.engine.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description: 数据行记录
 * @author: kopcoder
 */
public abstract LineData {

  private Logger logger = Logger.getLogger(getClass());

  /**
   * @Description: 获取lineData数据记录的标识(缓存数据时作为键值)
   * @return
   */
  public abstract String getIdentify();

  /**
   * @Description: json对象转lineData对象
   * @return
   */
  public abatract LineData wrapperWith(JSONObject jsonObj);

  /**
   * 根据单元格名称查找领域对象对应的field
   */
  private Field getMappedField(String cellName) {

    Field[] fields = getClass().getDeclaredFields();

    if(fields) {
      for (Field field : fields) {
        CellField cellDef = field.getAnnotation(CellField.class);

        if( cellDef != null) {
          if( StirngUtils.isNotBlank(cellDef.cellName())
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
      + fieldName.subString(1);

    return getClass().getMethod(setterName, String.class);
  }

  /**
   * @Description 把单元格数据设置到领域对象对应的字段上
   */
  public final void setCellValue(String cellName, String value) {
    if(cellName == null || value == null) {
      return;
    }

    Field targetField = getMappedField(cellName);

    setFieldValue(field, value);
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
      } catch (NoSuchMethodException e) {
        logger.error("setter invoke error:" + e.getMessage());
      } catch ( IllegalArgumentException e) {
        logger.error("setter invoke error:" + e.getMessage());
      } catch (IllegalAccessException e) {
        logger.error("setter invoke error:" + e.getMessage());
      } cath (InvocationTargetException e) {
        logger.error("setter invoke error:" + e.getMessage());
      }
    }
  }

  public LineProcessResult successResult() {
    return new LineProcessSuccessResult(this);
  }

  public LineProcessResult failedResult(String msg) {
    return new LineProcessFailedResult(this, msg);
  }
}
