package com.example.demo.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
import com.example.demo.model.PedidoLinea;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProductoRepository;
@Primary
@Service("pedidoServiceDB")
public class PedidoServiceDB implements PedidoService {
	
	@Autowired
	PedidoRepository repositorio;
	@Autowired
	ProductoRepository repositorioproductos;
	@Autowired
	PedidoLineaServiceDB servicioPedidoLinea;
	
	private Integer[] cantidades;
	private List<PedidoLinea> aux=new ArrayList<>();

	

	@Override
	public Pedido add(Pedido p) {
		return repositorio.save(p);
	}

	@Override
	public List<Pedido> findAll() {
		return repositorio.findAll();
	}

	@Override
	public boolean remove(int idpedido) {		
		repositorio.deleteById(idpedido);
		return true;
	}

	@Override
	public Pedido edit(Pedido pedido) {
		return null;
	}

	
	public Pedido crearPedido(Integer [] cantidades) {
		List<Producto> productos=repositorioproductos.findAll();
		Pedido p=new Pedido();
		PedidoLinea linea=new PedidoLinea();
		for (int j = 0; j < cantidades.length; j++) {
			linea.setCantidad(j);
			linea.setPedido(p);
			linea.setProducto(productos.get(j));
		}
		return p;
	}

	@Override
	public void addPedido(Usuario usuario, String tipopedido,List<PedidoLinea> listaproductos,String direccionentrega,
			String emailcontacto,String telefonocontacto) {
		//aqui iba new pedido
		Pedido ped=repositorio.getById(id);
		ped.setListaproductos(listaproductos);
		ped.setDireccionentrega(direccionentrega);
		ped.setEmailcontacto(emailcontacto);
		ped.setTelefonopedido(telefonocontacto);
		ped.setTipopedido(tipopedido);
		//Aqui se anade una vez
		repositorio.save(ped);
		usuario.addPedido(ped);
		for (int i = 0; i < listaproductos.size(); i++) {
			this.servicioPedidoLinea.add(listaproductos.get(i));			
		}
	}
	
	@Override
	public List<Pedido> findPedidosUsuario(Usuario usuario) {
		List<Pedido> pedidos;
		pedidos=usuario.getListapedidios();
		return pedidos;
	}

	public List<PedidoLinea> getAux() {
		return aux;
	}

	public void setAux(List<PedidoLinea> aux) {
		this.aux = aux;
	}

	public Integer[] getCantidades() {
		return cantidades;
	}

	public void setCantidades(Integer[] cantidades) {
		this.cantidades = cantidades;
	}
	public Pedido crearLinea(Integer [] cantidades,Pedido nuevopedido) {
		List<Producto> productos=repositorioproductos.findAll();
		//Aqui se anade pero no se deberia
		repositorio.save(nuevopedido);
		for (int i = 0; i < cantidades.length; i++) {
			PedidoLinea linea=new PedidoLinea(cantidades[i],productos.get(i),nuevopedido);
			nuevopedido.addLinea(linea);
			servicioPedidoLinea.add(linea);
			
		}
		return nuevopedido;
	}

}
