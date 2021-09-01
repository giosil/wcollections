package org.dew.test;

import java.util.Calendar;

import org.dew.util.WUtil;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestWCollections extends TestCase {
  
  public TestWCollections(String testName) {
    super(testName);
  }
  
  public static Test suite() {
    return new TestSuite(TestWCollections.class);
  }
  
  public void testApp() {
    String sValue = "3,14";
    double dValue = WUtil.toDouble(sValue, 0.0d);
    System.out.println("WUtil.toDouble(" + sValue + ", 0.0d) -> " + dValue);
    assertTrue(dValue == 3.14d);
    
    sValue = "1974-11-19";
    Calendar calValue = WUtil.toCalendar(sValue, null);
    System.out.println("WUtil.toCalendar(" + sValue + ", null) -> " + WUtil.formatDateTime(calValue, "-", true));
    assertNotNull(calValue);
    assertTrue(calValue.get(Calendar.DATE) == 19);
    
    sValue = " 00123 ";
    int iValue = WUtil.toInt(sValue, 0);
    System.out.println("WUtil.toInt(" + sValue + ", 0) -> " + iValue);
    assertTrue(iValue == 123);
   }
  
}
