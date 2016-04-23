package com.kopcoder.dataTransfer.excel.engine;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class SheetLineProcessor<T extends LineData, V extends LineDataValidator<T>> {

  private Logger logger = Logger.getLogger(getClass());

  private V validator = null;

  public SheetLineProcessor(V validator, ) {
    super();
    this.validator = validator;
  }

  /**
   * @description 解析sheet中指定行数据，并转换为指定class的对象,验证对象数据，并返回解析结果
   * @param sheet    解析的sheet
   * @param lineNum  sheet中的行号
   * @param targetObj    目标对象
   */
  public LineProcessResult processLineData(Sheet sheet,
    int lineNum, T targetObj) {

    T lineData = readLine(sheet, lineNum, targetObj);

    if(lineData == null) {
      return null;
    }

    try {
      if(validator != null) {
        validator.validate(lineData);
      }

      return lineData.successResult();
    } catch (Exception e) {
      return lineData.failedResult(e.getMessage);
    }
  }

  /**
   * @description 解析sheet中指定行数据，并转换为指定class的对象
   * @param sheet    解析的sheet
   * @param lineNum  sheet中的行号
   * @param targetObj    目标对象
   */
  private T readLine(Sheet sheet, int lineNum, T targetObj) {
    if(sheet == null || clazz == null) {
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
      case HSSFCell.CELL_TYPE_NUMBERIC:
        HSSFDataFormatter dataFormater = new HSSFDataFormatter();
        return dataFormater.format(valueCell);
    }

    return null;
  }
}
