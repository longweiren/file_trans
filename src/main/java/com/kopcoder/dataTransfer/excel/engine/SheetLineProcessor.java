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

import com.kopcoder.dataTransfer.excel.engine.exception.LineDataException;

import com.kopcoder.dataTransfer.excel.engine.model.LineData;
import com.kopcoder.dataTransfer.excel.engine.model.LineProcessResult;

/**
 * Excel 行处理器.
 * @param <T>  Excel行数据对应的Java对象
 * @author kopcoder
 */
public class SheetLineProcessor<T extends LineData> {

  private Logger logger = LogManager.getLogger(getClass());

  /*Excel行数据验证器*/
  private LineDataValidator<T> validator;
  /*Excel行数据业务处理器*/
  private LineDataHandler<T> lineDataHandler;

  public SheetLineProcessor(LineDataValidator<T> validator, LineDataHandler<T> dataHandler) {
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
   * @throws LineDataException 行数据转换成对象发生错误时抛出该异常
   */
  public LineProcessResult processLineData(Sheet sheet,
    int lineNum, boolean singleFlush, T targetObj) throws LineDataException {

    // 读取并解析Excel行数据
    T lineData = readLine(sheet, lineNum, targetObj);

    // 是否已读取到最后一行数据
    if(lineData == null) {
      return null;
    }

    try {
      if(validator != null) {
        // 如果已配置验证器，用验证器检查行数据
        validator.validate(lineData);
      }

      if(singleFlush && lineDataHandler != null) {
        // 对行数据做业务处理
        lineDataHandler.handle(lineData);
      }

      // 返回成功处理结果
      return lineData.successResult();
    } catch (Exception e) {
      // 返回失败处理结果
      return lineData.failedResult(e.getMessage());
    }
  }

  /**
   * @description 解析sheet中指定行数据，并转换为指定class的对象
   * @param sheet    解析的sheet
   * @param lineNum  sheet中的行号
   * @param targetObj    目标对象
   * @throws LineDataException 行数据转换成对象发生错误时抛出该异常
   */
  private T readLine(Sheet sheet, int lineNum, T targetObj) throws LineDataException {
    if(sheet == null || targetObj == null) {
      return null;
    }

    // Excel定义行
    Row defRow = sheet.getRow(0);
    // Excel数据行
    Row valueRow = sheet.getRow(lineNum);

    if(defRow == null || valueRow == null) {
      return null; // todo 区分defRow null or valueRow null
    }

    int cellIdx = 0;
    Cell defCell = null;
    boolean allCellNull = true;
    // 顺序读取单元格，转换数据并赋值给目标对象
    while((defCell = defRow.getCell(cellIdx)) != null) {
      String defCellName = defCell.getStringCellValue();

      if(defCellName != null) {
        // 去掉列头定义中的特殊值: [*]
        defCellName = defCellName.trim().replaceAll("\\*", "");
      }
      if(StringUtils.isBlank(defCellName)) {
        // 定义单元格没有内容(该列数据也不会转换)
        continue;
      }

      // 读取数据单元格
      Cell valueCell = valueRow.getCell(cellIdx++);

      if(valueCell != null) {
        // 单元格数据全部转换为String类型
        String value = getStringValue(valueCell);
        if(StringUtils.isNotBlank(value)) {
          value = value.trim();
          allCellNull = false;
        }

        // 目标对象相应属性赋值
        targetObj.setCellValue(defCellName, value);
      }
    }

    // 如果所有属性都是空值，说明是最后一行数据（或异常情况），不再处理后续行
    return allCellNull ? null : targetObj;
  }

  /**
   * @description 单元格数据全部转换为String类型
   * @param valueCell 数据单元格
   */
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
