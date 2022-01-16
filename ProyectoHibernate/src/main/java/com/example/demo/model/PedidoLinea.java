package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PedidoCantidadProducto")
public class PedidoLinea {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer lineapedidoId;
	private Integer cantidad;
	@ManyToOne
	private Producto producto;
	@ManyToOne
	private Pedido pedido;
	public PedidoLinea() {
		super();
	}

	public PedidoLinea(Integer cantidad, Producto producto, Pedido pedido) {
		super();
		this.cantidad = cantidad;
		this.producto = producto;
		this.pedido = pedido;

	}

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
	
	
	
}
