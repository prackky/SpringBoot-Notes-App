/**
 * 
 */
package com.sbnote.utility;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.sbnote.utility.IdManager;

/**
 * @author Prakhar.Rastogi
 *
 */
public class IdManagerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.sbnote.utility.IdManager#generateRandomPassword()}.
	 */
	@Test
	public void testGenerateRandomPassword() {
		String test = IdManager.generateRandomPassword();
		assertNotNull(test);
		//fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.sbnote.utility.IdManager#generateUserId(int, java.lang.String)}.
	 */
	@Test
	public void testGenerateUserId() {
		String test = IdManager.generateUserId(8, "test name");
		assertNotNull("Test successful", test);
	}
	
	/*@Test(expected = IllegalArgumentException.class)
	public void testGenerateUserIdWithNull() {
		IdManager.generateUserId(8, "");

	}*/

}
