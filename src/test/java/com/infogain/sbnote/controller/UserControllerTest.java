/**
 * 
 */
package com.sbnote.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sbnote.SbNoteApplication;
import com.sbnote.model.User;
import com.sbnote.service.UserService;

/**
 * @author Prakhar.Rastogi
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SbNoteApplication.class)
@SpringBootTest
public class UserControllerTest {
	
	@Autowired
    private WebApplicationContext wac;
	
	@MockBean
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@MockBean
	private UserService userService;
	
	private MockMvc mockMvc;

	User userReturn;
	User userRegister;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		
		//userReturn = new User("prakhar rastogi", "rastogi.prakhar@live.com");
		userReturn = new User((long) 2,"prastosb","Tyrion Lannister", "asdjkawhudhwhdu", "tyrion.lannister@westeros.com");
		userRegister = new User("Tyrion Lannister", "tyrion.lannister@westeros.com","password");
		
		Mockito.when(userService.findByEmail("SUCCESS_TEST@email.com")).thenReturn(null);
		Mockito.when(userService.findByEmail("FAILURE_TEST@email.com")).thenReturn(userReturn);
		Mockito.when(userService.createUser(Mockito.any())).thenReturn(true);
		Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("testPassword");
		
	}

	/**
	 * Test method for {@link com.sbnote.controller.UserController#createNewUser(com.sbnote.model.User)}.
	 */
	@Test
	public void testCreateNewUserWithExistingEmail() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Tyrion Lannister\",\"email\":\"FAILURE_TEST@email.com\",\"password\":\"myPassword\"}")
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder)
		.andExpect(status().isConflict());
		verify(userService, times(1)).findByEmail(Mockito.anyString());
		
		System.out.println("CreateNewUser With Existing Email successfully executed...");
		//throw new RuntimeException("not yet implemented");
		
	}
	
	@Test
	public void testCreateNewUser() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Tyrion Lannister\",\"email\":\"SUCCESS_TEST@email.com\",\"password\":\"myPassword\"}")
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder)
		.andDo(print())
		.andExpect(status().isOk());
		
		verify(userService, times(1)).createUser(Mockito.any());
		
		System.out.println("CreateNewUser successfully executed...");
		//throw new RuntimeException("not yet implemented");
		
	}

	/**
	 * Test method for {@link com.sbnote.controller.UserController#login(com.sbnote.model.User)}.
	 */
	@Test
	public void testLogin() throws Exception {
	}

}
