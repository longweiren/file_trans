package com.kopcoder.dataTransfer.excel.engine.model;

import java.util.Map;
import java.util.HashMap;

/**
 * @description 解析数据缓存对象
 * @author kopcoder
 */
public class LineDataCache<T extends LineData> {
  private Map<String, T> cacheData = new HashMap<String, T>();

  /**
   * @description 根据业务对象标识从缓存提取业务对象
   * @param identify 业务对象标识
   */
  public T findByIdentify(String identify) {
    T data = cacheData.get(identify);
    return data;
  }

  /**
   * @description 把业务对象加入缓存
   * @param lineData 待加入缓存的业务对象
   */
  public void add(T lineData) {
    if(lineData != null) {
      cacheData.put(lineData.getIdentify(), lineData);
    }
  }
}
