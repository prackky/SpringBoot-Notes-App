package com.sbnote.utility;

import java.util.Date;

import com.sbnote.service.RedisService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

    public static String generateToken(String signingKey, String subject) {
    	String token = "";
    	
    	if (RedisService.INSTANCE.hexists(subject)) {
        	System.out.println("Valid token already exists in redis.");
            token = RedisService.INSTANCE.hget(subject);
        }
    	else {
    		token = Jwts.builder()
				.setSubject(subject)
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, signingKey)
				.compact();
    		System.out.println("Token generated for user: " + subject + " is : " + token);
    		RedisService.INSTANCE.hsetnx(subject, token);
    		System.out.println("Token saved to redis server.");
    	}
        return token;
    }

    public static String parseToken(String token, String signingKey){
        if(token == null) {
            return null;
        }

        String subject = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
        if (!RedisService.INSTANCE.hexists(subject)) {
        	System.out.println("Token not a member in redis.");
            return null;
        }

        return subject;
    }

    public static Long invalidateRelatedTokens(String token, String subject) {
        return RedisService.INSTANCE.hdel(subject, token);
    }
}
