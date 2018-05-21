package com.sbnote.utility;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

public class IdManager {

	public static String generateRandomPassword() {

		System.out.println("start generating password...");
		List<CharacterRule> rules = Arrays.asList(
				new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1), 
				new CharacterRule(EnglishCharacterData.Digit, 1));
		
		PasswordGenerator generator = new PasswordGenerator();
		String password = generator.generatePassword(8, rules);
		return password;
	}
	
	public static String generateUserId(int len, String name) {
	    String[] AB = name.trim().split("\\s+");
	    String test = String.join("", AB);
	    System.out.println("User ID to be generated from: " + test);
	    
	    Random rnd = new Random();

	    StringBuilder sb = new StringBuilder(len);
	    for (int i = 0; i < len; i++) {
	        sb.append(test.charAt(rnd.nextInt(test.length())));
	    }
	    return sb.toString();
	    
	    /*String id = Arrays.stream(AB).;
	    
	    List<CharacterRule> rules = Arrays.asList(
				new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1), 
				new CharacterRule(EnglishCharacterData.Digit, 1));
		
		PasswordGenerator generator = new PasswordGenerator();
		String userId = generator.generatePassword(8, rules);
		return userId;
	    */
	    
	}
	
}
