package com.proyecto.app.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled  = true, prePostEnabled = true, securedEnabled = true)
public class MethodSecurity extends GlobalMethodSecurityConfiguration{

	
	
}
