package com.kopcoder.dataTransfer.excel.engine.typeHandler;

import java.util.Map;
import java.util.HashMap;

/**
 * @description 类型转换工
 * @author kopcoder
 */
public class TypeHandlerFactory {

  private static final Map<Class, TypeHandler> handlerMap;

  static {
    handlerMap = new HashMap<Class, TypeHandler>();
    handlerMap.put(Integer.class, new IntegerTypeHandler());
    handlerMap.put(Boolean.class, new BooleanTypeHandler());
  }

  /**
   * @description 新增类型转换工厂
   * @clazz       需要转换的目标类型
   * @typeHandler 类型转换器
   */
  public static void add(Class clazz, TypeHandler typeHandler) {
    handlerMap.put(clazz, typeHandler);
  }

  /**
   * @description 获取目标类型的转换器
   * @clazz       需要转换的目标类型
   */
  public static TypeHandler get(Class clazz) {
    return handlerMap.get(clazz);
  }
}
