package com.proyecto.app.security;

import static com.proyecto.app.security.Constants.HEADER_AUTHORIZACION_KEY;
import static com.proyecto.app.security.Constants.SUPER_SECRET_KEY;
import static com.proyecto.app.security.Constants.TOKEN_BEARER_PREFIX;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{


	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String header = request.getHeader(HEADER_AUTHORIZACION_KEY);

	    if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
	      chain.doFilter(request, response);	      
	      return;
	    }
	    
	    UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    chain.doFilter(request, response);
}
	
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
	    String token = request.getHeader(HEADER_AUTHORIZACION_KEY);

	    if (token != null) {

	    	
	    	Claims body = Jwts.parser()
	    			  .setSigningKey(SUPER_SECRET_KEY)
	    	          .parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
	    	          .getBody();

	    	      String user = body.getSubject();
	    	     
	    	      var roles = Arrays.stream(body.get("role")
	    	            .toString().split(","))
	    	            .map(SimpleGrantedAuthority::new)
	    	            .collect(Collectors.toList());


			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, roles);
			}
			return null;
		}
		return null;
	  }
	
}
