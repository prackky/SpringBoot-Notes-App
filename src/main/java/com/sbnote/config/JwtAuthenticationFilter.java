package com.sbnote.config;

import static com.sbnote.constants.SecurityConstants.HEADER_STRING;
import static com.sbnote.constants.SecurityConstants.SECRET;
import static com.sbnote.constants.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.sbnote.utility.JwtUtil;

import io.jsonwebtoken.SignatureException;

public class JwtAuthenticationFilter extends GenericFilterBean {

	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final String authHeader = request.getHeader(HEADER_STRING);

		if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
			((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing authentication token.");
			//throw new ServletException("Missing authentication token.");
			return;
		}

		final String token = authHeader.substring(7);
		System.out.println(token);

		try {
			//final Claims claims = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody();
			final String subject = JwtUtil.parseToken(token, SECRET);
			if(subject == null) {
				((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid authentication, please login.");
				//throw new ServletException("Invalid authentication, please login.");
				return;
			}
			System.out.println("Subject = " + subject);
			request.setAttribute("userId", subject);
			//request.setAttribute("claims", claims);
		} 
		catch (final SignatureException e) {
			throw new ServletException("Invalid token");
		}

		chain.doFilter(req, res);
		}
}
