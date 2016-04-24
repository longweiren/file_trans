package com.kopcoder.dataTransfer.excel.engine;

import java.io.InputStream;
import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.kopcoder.dataTransfer.excel.engine.model.SheetProcessResult;
import com.kopcoder.dataTransfer.excel.engine.model.LineProcessResult;

import com.kopcoder.dataTransfer.excel.engine.model.LineData;

public class SheetProcessor<T extends LineData> {

  private Logger logger = LogManager.getLogger(getClass());

  private String sheetName;
  private Class<T>  targetClazz;
  private SheetLineProcessor<T> lineProcessor;

  public SheetProcessor(String sheetName, Class<T> targetClazz, SheetLineProcessor<T> lineProcessor) {
    super();
    this.sheetName = sheetName;
    this.targetClazz = targetClazz;
    this.lineProcessor = lineProcessor;
  }

  /**
   * @Description:    从输入流提取Excel文件内容，并获取指定sheet内容
   * @param: inStream   excel文件输入流
   * @return: Sheet     提取的Sheet对象
   */
  protected Sheet getSheet(InputStream inStream) {
    Workbook workbook = null;
    Sheet sheet = null;
    try {
      workbook = WorkbookFactory.create(inStream);
      sheet = workbook.getSheet(sheetName);
    } catch (IOException e) {
      logger.warn(e.getMessage());
    } catch (EncryptedDocumentException e) {
      logger.warn(e.getMessage());
    } catch (InvalidFormatException e) {
      logger.warn(e.getMessage());
      logger.warn(e.getMessage());
    }

    return sheet;
  }

  /**
   * @Description: 从输入流读取excel内容，并从startLineNum行开始处理Sheet数据
   * @param inStream      excel输入流
   * @param startLineNum  处理的起始行
   * @param singleFlush   是否解析完单行记录就开始业务处理该行记录
   * @return SheetProcessResult
   */
  public SheetProcessResult process(InputStream inStream, int startLineNum, boolean singleFlush) {
    Sheet sheet = getSheet(inStream);
    if(sheet == null) {
      return null;
    }

    logger.info("prepare process sheet " + sheetName);
    return processLine(sheet, startLineNum, singleFlush);
  }

  /**
   * @Description:
   * @param: sheet        解析数据的Sheet
   * @param: lineNum      解析数据的行号
   * @param: singleFlush  是否解析完单行记录就开始业务处理该行记录
   */
  protected SheetProcessResult processLine(Sheet sheet, int lineNum, boolean singleFlush) {
    SheetProcessResult sheetResult = new SheetProcessResult();
    int processLineNum = lineNum;

    LineProcessResult lineResult = lineProcessor.processLineData(sheet, processLineNum, singleFlush, newModel());
    if(lineResult != null) {
      sheetResult.add(lineResult);
    }

    while(lineResult != null) {
      processLineNum += 1;
      lineResult = lineProcessor.processLineData(sheet, processLineNum, singleFlush, newModel());

      if(lineResult != null) {
        sheetResult.add(lineResult);
      }
    }

    return sheetResult;
  }

  private T newModel() {
    try {
      return targetClazz.newInstance();
    } catch (InstantiationException e) {
      logger.error("业务对象实例化异常：" + e.getMessage());
    } catch (IllegalAccessException e) {
      logger.error("业务对象实例化异常：" + e.getMessage());
    }
    return null;
  }
}
