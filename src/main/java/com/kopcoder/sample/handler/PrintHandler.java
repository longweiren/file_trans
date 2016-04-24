package com.kopcoder.sample.handler;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.kopcoder.dataTransfer.excel.engine.handler.LineDataHandler;
import com.kopcoder.dataTransfer.excel.engine.model.LineData;

public class PrintHandler<T extends LineData> implements LineDataHandler<T> {
  private Logger logger = LogManager.getLogger(getClass());

  @Override
  public void handle(T lineData) {
    logger.info(lineData);
  }
}
