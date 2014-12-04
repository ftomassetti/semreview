package it.polito.softeng.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import it.polito.softeng.common.StringUtils;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void testRemovePostfix() {
		assertEquals("",StringUtils.removePostfix("123", "123"));
		assertEquals("ab",StringUtils.removePostfix("ab123", "123"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemovePostfixException() {
		StringUtils.removePostfix("123a", "123");
	}
	
	@Test
	public void testRemoveVoidPostfix() {
		assertEquals("",StringUtils.removePostfix("", ""));
		assertEquals("Abc",StringUtils.removePostfix("Abc", ""));		
	}

}
