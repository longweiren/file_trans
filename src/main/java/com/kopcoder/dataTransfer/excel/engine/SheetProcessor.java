package com.kopcoder.dataTransfer.excel.engine;

import java.io.InputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WookbookFactory;

public abstract class SheetProcessor<T extends LineData, V extends LineDataValidator<T>> {

  private String sheetName;
  private SheetLineProcessor<T, V> lineProcessor

  public ExcelSheetProcess(String sheetName, SheetLineProcessor<T, V> lineProcessor) {
    super();
    this.sheetName = sheetName;
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

    } catch (EncryptedDocumentException e) {

    } catch (InvalidFormatException e) {

    } finally {

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

    return processLine(sheet, startLineNum, singleFlush);
  }

  /**
   * @Description:
   * @param: sheet        解析数据的Sheet
   * @param: lineNum      解析数据的行号
   * @param: singleFlush  是否解析完单行记录就开始业务处理该行记录
   */
  protected SheetProcessResult processLine(Sheet sheet, int lineNum, boolean singleFlush) {
    SheetProcessResult sheetResult = new SeetProcessResult();

    LineProcessResult lineResult = lineProcessor.processLineData(sheet, lineNum, singleFlush);
    if(lineResult != null) {
      sheetResult.add(lineResult);
    }

    while(lineResult != null) {
      lineResult = lineProcessor.processLineData(sheet, lineNum, singleFlush);

      if(lineResult != null) {
        sheetResult.add(lineResult);
      }
    }
  }
}
