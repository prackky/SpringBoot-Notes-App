package com.infogain.sbnote.utility;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.sbnote.utility.JwtUtil;

/**
 * @author Prakhar.Rastogi
 *
 */
public class JwtUtilTest {

	/**
	 * @throws java.lang.Exception
	 */
	
	String token = "";
	String key = "";
	String subject = "";
	
	@Before
	public void setUp() throws Exception {
		key = "secretKey";
	}

	/**
	 * Test method for {@link com.sbnote.utility.JwtUtil#generateToken(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGenerateToken() throws Exception {
		token = JwtUtil.generateToken(key, subject);
		assertNotNull(token);
	}

	/**
	 * Test method for {@link com.sbnote.utility.JwtUtil#parseToken(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testParseToken() throws Exception {
		
	}

	/**
	 * Test method for {@link com.sbnote.utility.JwtUtil#invalidateRelatedTokens(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testInvalidateRelatedTokens() throws Exception {
		Long l = JwtUtil.invalidateRelatedTokens(token, subject);
		assertNotNull(l);
	}

}
