package com.kopcoder.dataTransfer.excel.engine;

import org.junit.Test;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.kopcoder.sample.model.User;
import com.kopcoder.sample.handler.PrintHandler;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;

import com.kopcoder.dataTransfer.excel.engine.SheetProcessor;
import com.kopcoder.dataTransfer.excel.engine.model.SheetProcessResult;

import com.kopcoder.dataTransfer.excel.engine.handler.LineDataHandler;
import com.kopcoder.dataTransfer.excel.engine.validator.LineDataValidator;


public class SheetProcessorTest {
  private Logger logger = LogManager.getLogger(getClass());

  @Before
  public void setUp() {
     logger.info("before test");
  }
  @After
  public void tearDown() {
    logger.info("after test");
  }
  @Test
  public void process() {
    LineDataHandler<User> handler = new PrintHandler<User>();
    LineDataValidator<User> validator = null;

    SheetLineProcessor<User> lineProcessor = new SheetLineProcessor<User>(validator, handler);

    SheetProcessor sheetProcessor = new SheetProcessor("user", User.class, lineProcessor);

    InputStream inStream = null;
    try {
     inStream = new FileInputStream(new File("E:\\workspace\\java\\file_trans\\src\\test\\resources\\userlist.xls"));
     SheetProcessResult result = sheetProcessor.process(inStream, 1, true);

     if(result != null) {
       Assert.assertNotNull(result.getSuccessResults());
       Assert.assertEquals(2, result.getSuccessResults().size());
     }
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail(e.getMessage());
    } finally {
      if(inStream != null) {
        try {
          inStream.close();
        } catch(Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
