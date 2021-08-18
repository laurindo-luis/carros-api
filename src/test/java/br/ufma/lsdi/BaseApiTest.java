package br.ufma.lsdi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufma.lsdi.api.security.jwt.JwtUtil;
import br.ufma.lsdi.api.users.UserService;

@SpringBootTest(classes = CarrosApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public abstract class BaseApiTest {
	
	@Autowired
	protected TestRestTemplate rest;
	
	@Autowired
	private UserService userService;
	
	private String token = null;
	
	protected HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+token);
		return headers;
	}
	
	@BeforeAll
	public void initAll() {
		UserDetails user = userService.getUserByLogin("admin");
		assertNotNull(user);
		token = JwtUtil.createToken(user);
		assertNotNull(token);
	}
	
	public <T> ResponseEntity<T> get(String url, Class<T> responseType) {
		return rest.exchange(url, HttpMethod.GET, 
				new HttpEntity<>(getHeaders()), responseType);
	}
	
	public <T> ResponseEntity<T> post(String url, Object body, Class<T> responseType) {
		return rest.exchange(url, HttpMethod.POST, new HttpEntity<>(body, getHeaders()), 
				responseType, body);
	}
	
	public <T> ResponseEntity<T> delete(String url, Class<T> responseType) {
		return rest.exchange(url, HttpMethod.DELETE, new HttpEntity<>(getHeaders()), 
				responseType);
	}
	
	public <T> ResponseEntity<T> put(String url, Object body, Class<T> responseType) {
		return rest.exchange(url, HttpMethod.PUT, new HttpEntity<>(body, getHeaders()), 
				responseType, body);
	}
}
