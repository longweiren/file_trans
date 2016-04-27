package com.kopcoder.dataTransfer.excel.engine.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kopcoder.dataTransfer.excel.engine.typeHandler.NonTypeHandler;

/**
 * @description 单元格对应领域对象属性注解
 * @author kopcoder
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CellField {
  /* 是否不能为空 */
  public abstract boolean required() default false;
  /* 定义单元格中的内容 */
  public abstract String cellName() default "";
  /* 数字内容格式 */
  public abstract String numberFormat() default "";
  /* 单元格转对象属性的类型转换器 */
  public abstract Class typeHandler() default NonTypeHandler.class;

  /*  */
  public abstract boolean resource() default false;
  /*  */
  public abstract String resourceType() default "";
  /*  */
  public abstract String resourceUrlField() default "";
  /*  */
  public abstract String resMapKeyField() default "";

}
