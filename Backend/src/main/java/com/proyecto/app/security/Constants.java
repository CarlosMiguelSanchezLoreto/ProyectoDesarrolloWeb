package com.proyecto.app.security;

public class Constants {

	// Spring Security

	public static final String LOGIN_URL = "/login";
	public static final String LOGOUT_URL = "/logout";
	public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";

	// JWT

	public static final String ISSUER_INFO = "API UrbanFood";
	public static final String SUPER_SECRET_KEY = "1234";
	public static final long TOKEN_EXPIRATION_TIME = 60000; // 1 min

}
