package com.proyecto.app.entidades;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Role")
public class Role {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @Column(name="id", nullable = false, unique = true)
    private Long id;
	
	
	 @Column(name="Nombre", nullable = true, unique = false)
	private String nombre;


	@OneToMany(fetch = FetchType.LAZY,mappedBy = "rol")
	private List<Cliente> clientes;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public List<Cliente> getClientes() {
		return clientes;
	}


	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
}