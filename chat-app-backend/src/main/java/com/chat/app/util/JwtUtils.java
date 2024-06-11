package com.chat.app.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chat.app.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {
	
	@Value("${app.secret}")
	private String secret;

	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}

	public String generateToken(String subject, String userId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userId);
		return genrateToken(claims, subject);
	}

	private String genrateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuer("Deepak")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(100000)))
				.signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
	}

	private Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
	}

	public String getUserIdFromToken(String token) {
		Claims claims = getClaims(token);
		return claims.get("userId", String.class);
	}

	public String getToken() {
		HttpServletRequest httpRequest = RequestContextHolder.getRequest();
		if (httpRequest != null) {
			String token = httpRequest.getParameter("Authorization");
			return token;
		}
		else
		{
			throw new UnauthorizedException(Constants.NO_TOKEN);
		}

	}
	}