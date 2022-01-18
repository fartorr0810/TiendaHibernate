package com.example.demo.service;

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
import com.example.demo.repository.UsuarioRepository;
@Primary
@Service("pedidoServiceDB")
public class PedidoServiceDB implements PedidoService {
	
	@Autowired
	PedidoRepository repositorio;
	@Autowired
	ProductoRepository repositorioproductos;
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
		this.servicioUsuario.edit(usuario);

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
			nuevopedido.addLinea(linea);
			servicioPedidoLinea.add(linea);
		}
		return nuevopedido;
	}

	@Override
	public Pedido findById(Integer id) {
		return repositorio.getById(id);
	}

}
