package com.proyecto.app.security;

import static com.proyecto.app.security.Constants.HEADER_AUTHORIZACION_KEY;
import static com.proyecto.app.security.Constants.ISSUER_INFO;
import static com.proyecto.app.security.Constants.SUPER_SECRET_KEY;
import static com.proyecto.app.security.Constants.TOKEN_BEARER_PREFIX;
import static com.proyecto.app.security.Constants.TOKEN_EXPIRATION_TIME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.app.entidades.Cliente;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	    this.authenticationManager = authenticationManager;
	  }
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			Cliente credenciales = new ObjectMapper().readValue(request.getInputStream(), Cliente.class);
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					credenciales.getUsername(), credenciales.getPassword(), new ArrayList<>()));
			
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		String roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
		
		
		
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", roles);
		claims.put("int", 1);
		claims.put("string", "texto");
		
		System.out.println("Usuario autenticado correctamente...");
		String token = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setIssuer(ISSUER_INFO)
				.setSubject(((org.springframework.security.core.userdetails.User)auth.getPrincipal()).getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SUPER_SECRET_KEY)
				.compact();
		
		response.addHeader(HEADER_AUTHORIZACION_KEY, TOKEN_BEARER_PREFIX + token);
	}

}
