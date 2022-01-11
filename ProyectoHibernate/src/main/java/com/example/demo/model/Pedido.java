package com.example.demo.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Pedidos")
public class Pedido {
	@Id @GeneratedValue
	private Integer id;
	//@ElementCollection
	//@CollectionTable(name="ListaProductos", joinColumns = @JoinColumn(name="Productos_id"))
    @OneToMany(//cascade = CascadeType.ALL, 
    		//orphanRemoval = true,
    		fetch = FetchType.EAGER)
	private List<Producto> listaproductos;
	private String tipopedido;
	private Date fechapedido;
	private String direccionentrega;
	private String emailcontacto;

	private String telefonopedido;
	
	public Pedido() {
		super();
	}
	
	public Pedido(List<Producto> listaproductos, String tipopedido, Date fechapedido, String direccionentrega,
			String emailcontacto, String telefonopedido) {
		super();
		this.listaproductos = listaproductos;
		this.tipopedido = tipopedido;
		this.fechapedido = fechapedido;
		this.direccionentrega = direccionentrega;
		this.emailcontacto = emailcontacto;
		this.telefonopedido = telefonopedido;
	}
	//TODO La fechaaa
	public Pedido(List<Producto> listaproductos, String tipopedido, String direccionentrega, String emailcontacto,
			String telefonopedido) {
		super();
		this.listaproductos = listaproductos;
		this.tipopedido = tipopedido;
		this.direccionentrega = direccionentrega;
		this.fechapedido=new Date();
		this.emailcontacto = emailcontacto;
		this.telefonopedido = telefonopedido;
	}
	public Pedido(List<Producto> listaproductos) {
		super();
		this.listaproductos = listaproductos;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Producto> getListaproductos() {
		return listaproductos;
	}
	public void setListaproductos(List<Producto> listaproductos) {
		this.listaproductos = listaproductos;
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
