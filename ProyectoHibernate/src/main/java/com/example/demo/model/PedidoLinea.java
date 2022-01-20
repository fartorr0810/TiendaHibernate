package com.example.demo.model;


import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//Entidad PedidoLinea
@Entity
@Table(name="PedidoCantidadProducto")
public class PedidoLinea {
	//Id autogenerada
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer lineapedidoId;
	private Integer cantidad;
	//Un producto puede estar en la lineas y pilla la id del producto
	@ManyToOne(fetch=FetchType.EAGER)
	private Producto producto;
	//Un pedido tiene varias lineas y pilla la id del pedido
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Pedido pedido;
	//Constructor
	public PedidoLinea() {
		super();
	}

	public PedidoLinea(Integer cantidad, Producto producto, Pedido pedido) {
		super();
		this.cantidad = cantidad;
		this.producto = producto;
		this.pedido = pedido;

	}
//	Getters y Setters
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
//HashCode y Equals
	@Override
	public int hashCode() {
		return Objects.hash(lineapedidoId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoLinea other = (PedidoLinea) obj;
		return Objects.equals(lineapedidoId, other.lineapedidoId);
	}
	
	
	
}
