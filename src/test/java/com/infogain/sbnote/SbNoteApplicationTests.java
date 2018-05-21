package com.infogain.sbnote;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

import com.infogain.sbnote.controller.NoteControllerTest;
import com.infogain.sbnote.utility.IdManagerTest;
import com.infogain.sbnote.utility.JwtUtilTest;
import com.sbnote.controller.UserController;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	NoteControllerTest.class,
	UserController.class,
	IdManagerTest.class,
	JwtUtilTest.class
})

@SpringBootTest
public class SbNoteApplicationTests {

	@Test
	public void contextLoads() {
	}

}
