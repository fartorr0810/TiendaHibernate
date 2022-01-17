package com.example.demo.service;


import java.util.ArrayList;
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
	private Integer auxid;
	


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
	public void terminarPedido(Usuario usuario,Pedido ped) {
		repositorio.save(ped);
		usuario.addPedido(ped);
		for (int i = 0; i < ped.getListaproductos().size(); i++) {
			this.servicioPedidoLinea.add(ped.getListaproductos().get(i));			
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
		this.setAuxid(nuevopedido.getId());
		return nuevopedido;
	}

	@Override
	public Pedido findById(Integer id) {
		return repositorio.getById(id);
	}

	public Integer getAuxid() {
		return auxid;
	}

	public void setAuxid(Integer auxid) {
		this.auxid = auxid;
	}
}
