package org.dew.test;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

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
    System.out.println("WUtil.toCalendar(" + sValue + ", null) -> " + s(calValue));
    assertNotNull(calValue);
    assertTrue(calValue.get(Calendar.DATE) == 19);
    
    sValue = " 00123 ";
    int iValue = WUtil.toInt(sValue, 0);
    System.out.println("WUtil.toInt(" + sValue + ", 0) -> " + iValue);
    assertTrue(iValue == 123);
    
    sValue = " 1 ";
    boolean bValue = WUtil.toBoolean(sValue, false);
    System.out.println("WUtil.toBoolean(" + sValue + ", false) -> " + bValue);
    assertTrue(bValue);
   }
   
  @SuppressWarnings({ "unchecked" })
  public static
  String s(Object object)
  {
    if(object == null) return "null";
    String sClassName = object.getClass().getName();
    if(object instanceof java.sql.Date) {
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(((java.sql.Date) object).getTime());
      return "(" + sClassName + ") " + WUtil.formatDateTime(cal, "IT", true);
    }
    if(object instanceof java.sql.Timestamp) {
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(((java.sql.Timestamp) object).getTime());
      return "(" + sClassName + ") " + WUtil.formatDateTime(cal, "IT", true);
    }
    if(object instanceof java.util.Date) {
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(((java.util.Date) object).getTime());
      return "(" + sClassName + ") " + WUtil.formatDateTime(cal, "IT", true);
    }
    if(object instanceof java.util.Calendar) {
      Calendar cal = (Calendar) object;
      return "(" + sClassName + ") " + WUtil.formatDateTime(cal, "IT", true);
    }
    if(object instanceof java.lang.CharSequence) {
      return "(" + sClassName + ") \"" + object + "\"";
    }
    if(object instanceof Collection) {
      return collectionToString((Collection<?>) object, "");
    }
    if(object instanceof Map) {
      return mapToString((Map<Object, Object>) object, "");
    }
    if(object.getClass().isArray()) {
      return arrayToString(object);
    }
    return "(" + sClassName + ") " + object;
  }
  
  public static
  String collectionToString(Collection<?> collection, String sSuffix)
  {
    String sClassName = collection.getClass().getName();
    String sResult = "(" + sClassName + ") [" + sSuffix;
    String sItems = "";
    Iterator<?> iterator = collection.iterator();
    while(iterator.hasNext()) {
      sItems += "," + s(iterator.next()) + sSuffix;
    }
    if(sItems.length() > 0) sItems = sItems.substring(1);
    sResult += sItems + "]" + sSuffix;
    return sResult;
  }
  
  public static
  String mapToString(Map<Object, Object> map, String sSuffix)
  {
    String sClassName = map.getClass().getName();
    String sResult = "(" + sClassName + ") {" + sSuffix;
    String sItems = "";
    Iterator<Map.Entry<Object, Object>> iterator = map.entrySet().iterator();
    while(iterator.hasNext()) {
      Map.Entry<Object, Object> entry = iterator.next();
      sItems += "," + entry.getKey() + "=" + s(entry.getValue()) + sSuffix;
    }
    if(sItems.length() > 0) sItems = sItems.substring(1);
    sResult += sItems + "}" + sSuffix;
    return sResult;
  }
  
  public static
  String arrayToString(Object array)
  {
    String sClassName = array.getClass().getName();
    String sResult = "(" + sClassName + ") [";
    String sItems = "";
    int iLength = Array.getLength(array);
    for(int i = 0; i < iLength; i++) {
      Object oItem = Array.get(array, i);
      sItems += "," + s(oItem);
    }
    if(sItems.length() > 0) sItems = sItems.substring(1);
    sResult += sItems + "]";
    return sResult;
  }
}
