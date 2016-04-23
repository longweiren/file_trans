package com.kopcoder.dataTransfer.excel.engine.model;

import java.util.Map;
import java.util.HashMap;

/**
 * @description 解析数据缓存对象
 * @author kopcoder
 */
public class LineDataCache<T extends LineData> {
  private Map<String, T> cacheData = new HashMap<String, T>();

  public T findByIdentify(String identify) {
    T data = cacheData.get(identify);
    return data;
  }

  public void add(T lineData) {
    if(lineData != null) {
      cacheData.put(lineData.getIdentify(), lineData);
    }
  }
}
