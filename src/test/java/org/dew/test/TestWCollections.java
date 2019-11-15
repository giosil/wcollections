package org.dew.test;

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
		assertTrue(dValue == 3.14d);
	}
	
}
