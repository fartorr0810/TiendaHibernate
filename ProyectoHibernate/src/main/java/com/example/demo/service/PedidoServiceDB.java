package com.example.demo.service;


import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
import com.example.demo.model.PedidoLinea;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.PedidoLineaRepository;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.UsuarioRepository;
@Primary
@Service("pedidoServiceDB")
public class PedidoServiceDB implements PedidoService {
	
	@Autowired
	PedidoRepository repositorio;
	@Autowired
	ProductoRepository repositorioproductos;
	@Autowired
	PedidoLineaRepository repositorioPedidoLinea;
	@Autowired
	PedidoLineaServiceDB servicioPedidoLinea;
	@Autowired
	UsuarioServiceDB servicioUsuario;
	@Autowired
	UsuarioRepository repositoriousuarios;
	@Override
	public Pedido add(Pedido p) {
		return repositorio.save(p);
	}
	public Integer addNumber(Pedido p) {
		repositorio.save(p);
		return p.getId();
	}
	public Pedido crearPedidoVacio() {
		return new Pedido();
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
		return repositorio.save(pedido);
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
		for (int i = 0; i < ped.getListaproductos().size(); i++) {
			this.servicioPedidoLinea.add(ped.getListaproductos().get(i));			
		}
		this.servicioUsuario.addPedido(usuario, ped);

	}
	@Override
	public List<Pedido> findPedidosUsuario(Usuario usuario) {
		List<Pedido> pedidos;
		pedidos=usuario.getListapedidios();
		return pedidos;
	}


	public Pedido crearLinea(Integer [] cantidades,Pedido nuevopedido) {
		List<Producto> productos=repositorioproductos.findAll();
		repositorio.save(nuevopedido);
		for (int i = 0; i < cantidades.length; i++) {
			PedidoLinea linea=new PedidoLinea(cantidades[i],productos.get(i),nuevopedido);
			servicioPedidoLinea.add(linea);
			nuevopedido.addLinea(linea);
		}
		return nuevopedido;
	}

	@Override
	public Pedido findById(Integer id) {
		return repositorio.findById(id).orElse(null);
	}

	public Pedido updatePedido(Pedido pedd,Integer[] cantidades) {
		int i=0;
		Iterator<PedidoLinea> it = pedd.getListaproductos().iterator();
		while(it.hasNext()) {
				PedidoLinea linea=it.next();
				linea.setCantidad(cantidades[i]);
				i=i+1;
		}
		return pedd;
	}
	public void borrar(Pedido e) {
		Iterator<PedidoLinea> it = e.getListaproductos().iterator();
		while(it.hasNext()) {
			PedidoLinea linea = it.next();
			this.repositorioPedidoLinea.delete(linea);
		}
		repositorio.delete(e);
	}
}
