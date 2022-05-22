package com.proyecto.app.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.proyecto.app.entidades.Cliente;
import com.proyecto.app.repositories.ClienteRepository;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Cliente cliente = clienteRepository.getByUsername(username);
		
		if (cliente != null) {
			
			return new User(cliente.getUsername(), cliente.getPassword(), new ArrayList<>());
		}
		throw new UsernameNotFoundException(username);		
	}

}
