/**
 * 
 */
package com.infogain.sbnote.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sbnote.SbNoteApplication;
import com.sbnote.controller.NoteController;
import com.sbnote.model.Note;
import com.sbnote.service.NoteService;

/**
 * @author Prakhar.Rastogi
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SbNoteApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NoteControllerTest {
	
	@Autowired
    private WebApplicationContext wac;
	@MockBean
	private NoteService noteService;
	@InjectMocks
	private NoteController noteController;

	private MockMvc mockMvc;
	
	Note mockNote1 = new Note();
	Note mockNote2 = new Note();
	Note mockNote3 = new Note();
	Note mockNote4 = new Note();
	String mockList = "";
	List<Note> mockNoteList = new ArrayList<Note>();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		
		mockNote1.setId((long) 9);
		mockNote1.setUserId("rkauurry");
		mockNote1.setTitle("New Title");
		mockNote1.setContent("content content content content");
		mockNoteList.add(mockNote1);
		
		mockNote2.setId((long) 10);
		mockNote2.setUserId("rkauurry");
		mockNote2.setTitle("New Title");
		mockNote2.setContent("content content content content");
		mockNoteList.add(mockNote2);
		
		mockNote3.setId((long) 21);
		mockNote3.setUserId("rkauurry");
		mockNote3.setTitle("New Title");
		mockNote3.setContent("content content content content");
		mockNoteList.add(mockNote3);
		
		mockNote4.setId((long) 10);
		mockNote4.setUserId("rkauurry");
		mockNote4.setTitle("Updated Title");
		mockNote4.setContent("Updated content content content content");
		
		mockList = "[{\"userId\":\"rkauurry\",\"title\":\"New Title\",\"content\":\"content content content content\",\"id\":\"9\"},{\"userId\":\"rkauurry\",\"title\":\"New Title\",\"content\":\"content content content content\",\"id\":\"10\"},{\"userId\":\"rkauurry\",\"title\":\"New Title\",\"content\":\"content content content content\",\"id\":\"21\"}]";
		
		Mockito.when(noteService.getAllNotes(Mockito.anyString())).thenReturn(mockNoteList);
		Mockito.when(noteService.createNote(Mockito.any())).thenReturn(mockNote1);
		Mockito.when(noteService.getNoteById(Mockito.any(), Mockito.any())).thenReturn(mockNote1);
		Mockito.when(noteService.updateNote(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(mockNote4);
	}

	/**
	 * Test method for {@link com.sbnote.controller.NoteController#getAllNotesForUser(java.lang.String)}.
	 */
	@Test
	public void testGetAllNotesForUser() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/notes")
				.requestAttr("userId", mockNote1.getUserId())
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "[{\"userId\":\"rkauurry\",\"title\":\"New Title\",\"content\":\"content content content content\",\"id\":9},{\"userId\":\"rkauurry\",\"title\":\"New Title\",\"content\":\"content content content content\",\"id\":10},{\"userId\":\"rkauurry\",\"title\":\"New Title\",\"content\":\"content content content content\",\"id\":21}]";
		assertEquals(expected, result.getResponse().getContentAsString());
		System.out.println("getAllNotesForUser successfully executed...");
		//throw new RuntimeException("not yet implemented");
	}

	/**
	 * Test method for {@link com.sbnote.controller.NoteController#createNoteForUser(com.sbnote.model.Note, java.lang.String)}.
	 */
	@Test
	public void testCreateNoteForUser() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/notes/9")
				.requestAttr("userId", mockNote1.getUserId())
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "{\"userId\":\"rkauurry\",\"title\":\"New Title\",\"content\":\"content content content content\",\"id\":9}";
		assertEquals(expected, result.getResponse().getContentAsString());
		System.out.println("getNoteById successfully executed...");
		
	}

	/**
	 * Test method for {@link com.sbnote.controller.NoteController#getNoteById(java.lang.Long, java.lang.String)}.
	 */
	@Test
	public void testGetNoteById() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"New Title\",\"content\":\"content content content content\"}")
				.requestAttr("userId", mockNote1.getUserId())
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "{\"userId\":\"rkauurry\",\"title\":\"New Title\",\"content\":\"content content content content\",\"id\":9}";
		assertEquals(expected, result.getResponse().getContentAsString());
		System.out.println("createNotesForUser successfully executed...");
		
	}

	/**
	 * Test method for {@link com.sbnote.controller.NoteController#updateNoteForUser(java.lang.Long, com.sbnote.model.Note, java.lang.String)}.
	 */
	@Test
	public void testUpdateNoteForUser() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/notes/10")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"Updated Title\",\"content\":\"Updated content content content content\"}")
				.requestAttr("userId", mockNote1.getUserId())
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "{\"userId\":\"rkauurry\",\"title\":\"Updated Title\",\"content\":\"Updated content content content content\",\"id\":10}";
		assertEquals(expected, result.getResponse().getContentAsString());
		System.out.println("updateNotesForUser successfully executed...");
		
	}

	/**
	 * Test method for {@link com.sbnote.controller.NoteController#deleteNoteForUser(java.lang.Long, java.lang.String)}.
	 */
	@Test
	public void testDeleteNoteForUser() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/notes/9")
				.requestAttr("userId", mockNote1.getUserId())
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder)
		.andExpect(status().isOk());
		
	}

	/**
	 * Test method for {@link com.sbnote.controller.NoteController#deleteAllNoteForUser(java.lang.String)}.
	 */
	@Test
	public void testDeleteAllNoteForUser() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/notes/all")
				.requestAttr("userId", mockNote1.getUserId())
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder)
		.andExpect(status().isOk());
	}

}
