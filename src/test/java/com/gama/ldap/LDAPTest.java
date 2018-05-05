package com.gama.ldap;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Arrays;


/**
 * lib-ldap unit test
 */
public class LDAPTest extends TestCase {
	
	/**
	 * Test LDAP.getAttr method 
	 */	
	public void testGetAttr() throws Exception {
		
		LDAP ldap = new LDAP();
		
		try {
			
			String result = ldap.getAttr("gama","uid");
			
			System.out.println(result);
			
			result = ldap.getAttr("gama","gid");
			
			System.out.println(result);
			
			assertTrue(true);
			
			
		} catch (Exception e) {
			
			assertTrue(false);
		}

	}
	
}