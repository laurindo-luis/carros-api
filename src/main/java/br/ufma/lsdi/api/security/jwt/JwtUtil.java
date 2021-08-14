package br.ufma.lsdi.api.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static java.util.Objects.nonNull;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	
	/*
	 * Chave com algoritmo HS512
	 * http://www.allkeysgenerator.com
	 */
	private static final String JWT_SECRET = "MbQeThWmZq4t7w!z%C*F-JaNdRfUjXn2r5u8x/A?D(G+KbPeShVkYp3s6v9y$B&E";
	
	public static String createToken(UserDetails user) {
		List<String> roles = user.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		
		byte[] signingKey = JwtUtil.JWT_SECRET.getBytes();
		
		int days = 10;
		long time = days * 24 * 60 * 60 * 1000;
		Date expiration = new Date(System.currentTimeMillis() + time);
		
		return Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
				.setSubject(user.getUsername())
				.setExpiration(expiration)
				.claim("rol", roles)
				.compact();
	}
	
	//Pega as credênciais do usuário
	public static Claims getClaims(String token) {
		byte[] signingKey = JwtUtil.JWT_SECRET.getBytes();
		token = token.replace("Bearer", "");
		return Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public static String getLogin(String token) {
		Claims claims = getClaims(token);		
		return nonNull(claims) ? claims.getSubject() : null;
	}
	
	public static List<GrantedAuthority> getRoles(String token) {
		Claims claims = getClaims(token);
		if(nonNull(claims)) {
			return ((List<?>) claims
					.get("rol"))
					.stream()
					.map(authority -> new SimpleGrantedAuthority((String) authority))
					.collect(Collectors.toList());
		}
		return null;
	}
	
	public static boolean isTokenValid(String token) {
		Claims claims = getClaims(token);
		if(nonNull(claims)) {
			String login = claims.getSubject();
			Date expiration = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			return nonNull(login) && nonNull(expiration) && now.before(expiration);
		}
		return false;
	}
}
