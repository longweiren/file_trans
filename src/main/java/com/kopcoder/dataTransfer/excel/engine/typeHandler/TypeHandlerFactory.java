package com.kopcoder.dataTransfer.excel.engine.typeHandler;

import java.util.Map;
import java.util.HashMap;

public class TypeHandlerFactory {

  private static final Map<Class, TypeHandler> handlerMap;

  static {
    handlerMap = new HashMap<Class, TypeHandler>();
    handlerMap.put(Integer.class, new IntegerTypeHandler());
    handlerMap.put(Boolean.class, new BooleanTypeHandler());
  }

  public static void add(Class clazz, TypeHandler typeHandler) {
    handlerMap.put(clazz, typeHandler);
  }

  public static TypeHandler get(Class clazz) {
    return handlerMap.get(clazz);
  }
}
