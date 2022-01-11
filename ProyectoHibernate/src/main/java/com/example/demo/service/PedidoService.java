package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;

public interface PedidoService {
	public Pedido add(Pedido p);
	public List<Pedido> findAll();
	public boolean remove(int idpedido);
	public Pedido edit(Pedido pedido);
	public Pedido crearPedido(Integer[] cantidades);
	public List<Producto> getListatemporal();
	public void addPedido(Usuario usuario,String tipopedido,List<Producto> listaproductos,String direccionentrega,
			String emailcontacto,String telefonocontacto);
	public List<Pedido> findPedidosUsuario(Usuario usuario);
}
