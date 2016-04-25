package com.kopcoder.dataTransfer.excel.engine.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kopcoder.dataTransfer.excel.engine.typeHandler.NonTypeHandler;

/**
 * 单元格对应领域对象属性注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CellField {
  public abstract boolean required() default false;
  public abstract String cellName() default "";
  public abstract String numberFormat() default "";
  public abstract Class typeHandler() default NonTypeHandler.class;

  public abstract boolean resource() default false;
  public abstract String resourceType() default "";
  public abstract String resourceUrlField() default "";
  public abstract String resMapKeyField() default "";

}
