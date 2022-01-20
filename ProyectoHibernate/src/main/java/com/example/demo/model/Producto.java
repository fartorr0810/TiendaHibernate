package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//Tabla Producto
@Entity
@Table(name="Productos")
public class Producto {
	//Id autogenerada
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	//Atributos del producto
	private Integer id;
	private String nombre;
	private Double precio;
	//Constructores
	public Producto() {
		super();
	}
	
	public Producto(String nombre, Double precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
	}

	//Getters y Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	//HashCode y Equals
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return Objects.equals(id, other.id);
	}
	
}
