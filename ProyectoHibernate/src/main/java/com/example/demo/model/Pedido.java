package com.example.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//Entidad usuario
@Entity
@Table(name="Pedidos")
public class Pedido {
	//Id Autogenerada
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//Relacion de un pedido tiene varias lineas.
    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
	private List<PedidoLinea> listalineas;
    //Datos del pedido
	private String tipopedido;
	private Date fechapedido;
	private String direccionentrega;
	private String emailcontacto;
	private String telefonopedido;
	
	//Constructor donde inicializa la lista  y la fecha actual del pedido
	public Pedido() {
		super();
		this.listalineas=new ArrayList<>();
		this.fechapedido=new Date();

	}	
	public Pedido(List<PedidoLinea> listaproductos, String tipopedido, Date fechapedido, String direccionentrega,
			String emailcontacto, String telefonopedido) {
		super();
		this.listalineas = listaproductos;
		this.tipopedido = tipopedido;
		this.fechapedido = fechapedido;
		this.direccionentrega = direccionentrega;
		this.emailcontacto = emailcontacto;
		this.telefonopedido = telefonopedido;
	}
	public Pedido(List<PedidoLinea> listaproductos, String tipopedido, String direccionentrega, String emailcontacto,
			String telefonopedido) {
		super();
		this.listalineas = listaproductos;
		this.tipopedido = tipopedido;
		this.direccionentrega = direccionentrega;
		this.fechapedido=new Date();
		this.emailcontacto = emailcontacto;
		this.telefonopedido = telefonopedido;
	}
	public Pedido(List<PedidoLinea> listaproductos) {
		super();
		this.listalineas = listaproductos;
	}
	//	Getters y Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<PedidoLinea> getListaproductos() {
		return listalineas;
	}
	public void setListaproductos(List<PedidoLinea> listaproductos) {
		this.listalineas = listaproductos;
	}
	public void addLinea(PedidoLinea linea) {
		this.listalineas.add(linea);
	}
	public String getTipopedido() {
		return tipopedido;
	}
	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}
	public Date getFechapedido() {
		return fechapedido;
	}
	public void setFechapedido(Date fechapedido) {
		this.fechapedido = fechapedido;
	}
	public String getDireccionentrega() {
		return direccionentrega;
	}
	public void setDireccionentrega(String direccionentrega) {
		this.direccionentrega = direccionentrega;
	}
	public String getEmailcontacto() {
		return emailcontacto;
	}
	public void setEmailcontacto(String emailcontacto) {
		this.emailcontacto = emailcontacto;
	}
	public String getTelefonopedido() {
		return telefonopedido;
	}
	public void setTelefonopedido(String telefonopedido) {
		this.telefonopedido = telefonopedido;
	}
	//HashCode y equals
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
		Pedido other = (Pedido) obj;
		return Objects.equals(id, other.id);
	}
	
}
