package com.sbnote.controller;

import static com.sbnote.constants.SecurityConstants.SECRET;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbnote.model.JwtResponse;
import com.sbnote.model.User;
import com.sbnote.service.UserService;
import com.sbnote.utility.IdManager;
import com.sbnote.utility.JwtUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
/*    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUser() {
    	 List<User> user = userService.getAllUsers();
    	 return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }*/
	
	
	@ApiOperation(value = "Create a new user", response = ResponseEntity.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully logged in"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
    @PostMapping(value = "/register")
    public ResponseEntity<?> createNewUser(@RequestBody User user) throws ServletException {
    	//String message = "";
		if(user.getPassword()==null || user.getPassword().isEmpty()) {
			throw new ServletException("Password cannot be empty.");
		}
		if(user.getEmail()==null || user.getEmail().isEmpty()) {
			throw new ServletException("Email cannot be empty.");
		}
    	User userExists = userService.findByEmail(user.getEmail());
    	if(userExists != null) {
    		System.out.println("Email already exist in database...");
    		return new ResponseEntity<String>("User already exists, please try to login...", HttpStatus.CONFLICT);
    	}
    	else {
    		String password = user.getPassword();
        	String userId = IdManager.generateUserId(8, user.getName().trim());
        	System.out.println("Password set by user " + user.getEmail() + " : " + password);
        	String encodedPassword = bCryptPasswordEncoder.encode(password);
        	user.setUserId(userId);
        	user.setPassword(encodedPassword);
    		boolean userCreated = userService.createUser(user);
    		
    		if(userCreated == true)
    			user.setPassword("enCryptedPassword");
    		else
    			return new ResponseEntity<String>("There was some issue creating the user, please try again.."
    					, HttpStatus.CONFLICT);
    	}
    	return new ResponseEntity<User>(user, HttpStatus.OK);
    }

	@ApiOperation(value = "Login to create new notes", response = ResponseEntity.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully logged in"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody User login) throws ServletException, IOException {

		String jwtToken = "";

		if (login.getUserId() == null || login.getPassword() == null) {
			//return new ResponseEntity<String>("Please fill in username and password", HttpStatus.BAD_REQUEST);
			//throw new ServletException("Please fill in username and password");
			throw new ServletException("UserID and Password not provided.");
		}

		String userId = login.getUserId();
		String password = login.getPassword();

		User user = userService.findByuserId(userId);

		if (user == null) {
			throw new ServletException("Invalid userID.");
		}

		String pwd = user.getPassword();

		if (!bCryptPasswordEncoder.matches(password, pwd)) {
			throw new ServletException("Invalid userID and password.");
		}

		jwtToken = JwtUtil.generateToken(SECRET, userId);

		if(jwtToken == null) {
			return new ResponseEntity<String>("issue while generating token...", HttpStatus.FAILED_DEPENDENCY);
		}
		//Cookie cookie = CookieUtil.create(response, jwtTokenCookieName, jwtToken, false, -1, "localhost");
		//return new ResponseEntity<String>(jwtToken, HttpStatus.ACCEPTED);
		String jsonString = "{\"status\":\"success\", \"token\":\"Bearer " + jwtToken + "\"}";
		ObjectMapper mapper = new ObjectMapper();
		JwtResponse obj = mapper.readValue(jsonString, JwtResponse.class);
		return new ResponseEntity<Object>(obj, HttpStatus.OK);
	}

}
