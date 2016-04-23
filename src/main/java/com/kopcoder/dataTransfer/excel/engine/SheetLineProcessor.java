package com.kopcoder.dataTransfer.excel.engine;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.kopcoder.dataTransfer.excel.engine.validator.LineDataValidator;
import com.kopcoder.dataTransfer.excel.engine.handler.LineDataHandler;

import com.kopcoder.dataTransfer.excel.engine.model.LineData;
import com.kopcoder.dataTransfer.excel.engine.model.LineProcessResult;

public class SheetLineProcessor<T extends LineData, V extends LineDataValidator<T>, H extends LineDataHandler<T>> {

  private Logger logger = LogManager.getLogger(getClass());

  private V validator = null;
  private H lineDataHandler = null;

  public SheetLineProcessor(V validator, H dataHandler) {
    super();
    this.validator = validator;
    this.lineDataHandler = dataHandler;
  }

  /**
   * @description 解析sheet中指定行数据，并转换为指定class的对象,验证对象数据，并返回解析结果
   * @param sheet    解析的sheet
   * @param lineNum  sheet中的行号
   * @param singleFlush  是否解析完单条数据即处理
   * @param targetObj    目标对象
   */
  public LineProcessResult processLineData(Sheet sheet,
    int lineNum, boolean singleFlush, T targetObj) {

    T lineData = readLine(sheet, lineNum, targetObj);

    if(lineData == null) {
      return null;
    }

    try {
      if(validator != null) {
        validator.validate(lineData);
      }

      if(singleFlush && lineDataHandler != null) {
        lineDataHandler.handle(lineData);
      }

      return lineData.successResult();
    } catch (Exception e) {
      return lineData.failedResult(e.getMessage());
    }
  }

  /**
   * @description 解析sheet中指定行数据，并转换为指定class的对象
   * @param sheet    解析的sheet
   * @param lineNum  sheet中的行号
   * @param targetObj    目标对象
   */
  private T readLine(Sheet sheet, int lineNum, T targetObj) {
    if(sheet == null || targetObj == null) {
      return null;
    }

    Row defRow = sheet.getRow(0);
    Row valueRow = sheet.getRow(lineNum);

    if(defRow == null || valueRow == null) {
      return null; // todo 区分defRow null or valueRow null
    }

    int cellIdx = 0;
    Cell defCell = null;
    boolean allCellNull = true;
    while((defCell = defRow.getCell(cellIdx)) != null) {
      String defCellName = defCell.getStringCellValue();

      if(defCellName != null) {
        // 去掉列头定义中的*值
        defCellName = defCellName.trim().replaceAll("\\*", "");
      }
      if(StringUtils.isBlank(defCellName)) {
        continue;
      }

      Cell valueCell = valueRow.getCell(cellIdx++);

      if(valueCell != null) {
        String value = getStringValue(valueCell);
        if(StringUtils.isNotBlank(value)) {
          value = value.trim();
          allCellNull = false;
        }

        targetObj.setCellValue(defCellName, value);
      }
    }

    return allCellNull ? null : targetObj;
  }

  private String getStringValue(Cell valueCell) {
    if(valueCell == null) {
      return null;
    }

    switch(valueCell.getCellType()) {
      case HSSFCell.CELL_TYPE_BLANK:
        return null;
      case HSSFCell.CELL_TYPE_STRING:
        return valueCell.getStringCellValue();
      case HSSFCell.CELL_TYPE_BOOLEAN:
        return String.valueOf(valueCell.getBooleanCellValue());
      case HSSFCell.CELL_TYPE_NUMERIC:
        HSSFDataFormatter dataFormater = new HSSFDataFormatter();
        return dataFormater.formatCellValue(valueCell);
    }

    return null;
  }
}
