package com.proyecto.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proyecto.app.entidades.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	@Query(value="select r from Role r where r.nombre=?1")
	List<Role> getByNombre (String rol);
	
}
