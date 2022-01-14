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
//	public List<Producto> resumenDelPedido(Integer[] cantidades){
//		List<Producto> listaproductosUnidades = null;
//		listaproductosUnidades.
//		
//		return listaproductosUnidades; 
//	}
	
	public Pedido crearPedido(Integer [] cantidades) {
		List<Producto> productos=repositorioproductos.findAll();
		Pedido p=new Pedido();
		PedidoLinea linea=new PedidoLinea();
		for (int j = 0; j < cantidades.length; j++) {
			linea.setCantidad(j);
			linea.setPedido(p);
			linea.setProducto(productos.get(j));
		}
		linea.setPedido(p);

		
		return p;
	}

	@Override
	public void addPedido(Usuario usuario, String tipopedido,ArrayList<PedidoLinea> listaproductos,String direccionentrega,
			String emailcontacto,String telefonocontacto) {
		Pedido p=new Pedido(listaproductos,tipopedido, direccionentrega, emailcontacto,
			telefonocontacto);
		repositorio.save(p);
		usuario.addPedido(p);
	}

	@Override
	public List<Pedido> findPedidosUsuario(Usuario usuario) {
		List<Pedido> pedidos;
		pedidos=usuario.getListapedidios();
		return pedidos;
	}

	@Override
	public List<Producto> getListatemporal() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer[] getCantidades() {
		return cantidades;
	}

	public void setCantidades(Integer[] cantidades) {
		this.cantidades = cantidades;
	}
	public Pedido crearLinea(Integer [] cantidades,Pedido nuevopedido) {
		Pedido p=new Pedido();
		List<Producto> productos=repositorioproductos.findAll();
		for (int i = 0; i < cantidades.length; i++) {
			PedidoLinea linea=new PedidoLinea(cantidades[i],productos.get(i),nuevopedido);
			p.addLinea(linea);
		}
		return p;
	}


}
