package com.proyecto.app.services;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proyecto.app.entidades.Cliente;
import com.proyecto.app.repositories.ClienteRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Cliente> clienteOptional = clienteRepository.getByUsername(username);
		
		if (clienteOptional.isPresent()) {
			
			Cliente cliente = clienteOptional.get();
			
			var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + cliente.getRol().getNombre().toUpperCase()));
			
			return new User(cliente.getUsername(), cliente.getPassword(), authorities);
		}
		throw new UsernameNotFoundException(username);		
	}

}
