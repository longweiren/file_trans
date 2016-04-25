package com.kopcoder.dataTransfer.excel.engine.utils;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.kopcoder.dataTransfer.excel.engine.annotation.CellField;
import com.kopcoder.dataTransfer.excel.engine.typeHandler.TypeHandler;
import com.kopcoder.dataTransfer.excel.engine.typeHandler.NonTypeHandler;

public class BeanUtils {
  private static Logger logger = LogManager.getLogger(BeanUtils.class);

  public static TypeHandler getTypeHandler(Field field) {

      CellField cellDef = field.getAnnotation(CellField.class);
       if( cellDef != null
          && cellDef.typeHandler() != null) {
        if(cellDef.typeHandler() == NonTypeHandler.class) {
          return null;
        }

        Class clazz = cellDef.typeHandler();

        try {
          return (TypeHandler)clazz.newInstance();
        } catch (InstantiationException e) {
          logger.error("TypeHandler实例化异常：" + e.getMessage());
        } catch (IllegalAccessException e) {
          logger.error("TypeHandler实例化异常：" + e.getMessage());
        }
      }
      return null;
  }

  /**
   * 根据单元格名称查找领域对象对应的field
   */
  public static Field getMappedField(Class clazz, String cellName) {

    Field[] fields = clazz.getDeclaredFields();

    if(fields != null) {
      for (Field field : fields) {
        CellField cellDef = field.getAnnotation(CellField.class);

        if( cellDef != null
          && StringUtils.isNotBlank(cellDef.cellName())
          && cellDef.cellName().equals(cellName)) {
            return field;
        }
      }
    }

    return null;
  }
  /**
   * @description 为对象指定属性赋值
   * @param targetObject 待赋值的目标对象
   * @param field 待赋值属性
   * @param vlaue 赋值内容
   */
  public static void setFieldValue(Object targetObject, Field field, Object value) {
    if(field == null) {
      return ;
    }

    // use field.set
    try {
      field.set(targetObject, value);
      return;
    } catch (IllegalArgumentException e) {
      logger.debug("set field value warning:" + e.getMessage());
    } catch (IllegalAccessException e) {
      logger.debug("set field value warning:" + e.getMessage());
    }

    // use setX method
    Method setMethod = getSetter(field);
    if(setMethod != null) {
      try {
        setMethod.invoke(targetObject, value);
      } catch (SecurityException e) {
        logger.error("setter invoke error:" + e.getMessage());
      } catch ( IllegalArgumentException e) {
        logger.error("setter invoke argument error:" + e.getMessage());
      } catch (IllegalAccessException e) {
        logger.error("setter invoke access error:" + e.getMessage());
      } catch (InvocationTargetException e) {
        logger.error("setter invoke error:" + e.getMessage());
      }
    }
  }

  private static Method getSetter(Field field) {
    String fieldName = field.getName();
    String setterName = "set" + Character.toUpperCase(fieldName.charAt(0))
      + fieldName.substring(1);

    try {
      return field.getDeclaringClass().getMethod(setterName, field.getType());
    } catch (NoSuchMethodException e) {
      logger.error("获取setter方法异常：" + e.getMessage());
      return null;
    }

  }
}
