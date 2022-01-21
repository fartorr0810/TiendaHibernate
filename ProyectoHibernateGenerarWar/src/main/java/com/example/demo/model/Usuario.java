package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
@Entity
@Table(name="Usuarios")
public class Usuario {
	//Id autogenerada
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//Atributos del usuario
	private String username;
	private String password;
	private String direccion;
	private String email;
	private String telefono;
	//Un usuario puede tener varios pedidos
	@OneToMany(cascade =CascadeType.ALL, orphanRemoval = true,
			fetch=FetchType.EAGER)
	private List<Pedido> listapedidios=new ArrayList<>();
	//Constructores
	public Usuario() {
		super();
	}
	
	public Usuario(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Usuario(String username, String password, String direccion, String email,String telefono, List<Pedido> listapedidios) {
		super();
		this.username = username;
		this.password = password;
		this.direccion = direccion;
		this.email = email;
		this.listapedidios = listapedidios;
		this.telefono=telefono;
	}
	//Getters y Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Pedido> getListapedidios() {
		return listapedidios;
	}
	public void setListapedidios(List<Pedido> listapedidios) {
		this.listapedidios = listapedidios;
	}
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void addPedido(Pedido p) {
		this.listapedidios.add(p);
	}
	//HashCode y Equals
	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}
	
}
