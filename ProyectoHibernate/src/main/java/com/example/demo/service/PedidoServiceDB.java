package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
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
		//Crear una lista de productos, no un pedido
		List<Producto> productos=repositorioproductos.findAll();
		Pedido p=new Pedido(productos);
		Iterator<Producto> sig = p.getListaproductos().iterator();
		int i=0;
		while (sig.hasNext()) {
			Producto producto = sig.next();
			producto.setCantidad(cantidades[i]);
			i++;			
		}		
		return p;
	}

	@Override
	public void addPedido(Usuario usuario, String tipopedido,List<Producto> listaproductos,String direccionentrega,
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

}
