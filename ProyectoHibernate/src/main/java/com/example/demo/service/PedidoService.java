package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
//Interfaz con metodos basicos y otros necesarios.
public interface PedidoService {
	public Pedido add(Pedido p);
	public List<Pedido> findAll();
	public boolean remove(int idpedido);
	public Pedido findById(Integer id);
	public Pedido edit(Pedido pedido);
	public List<Pedido> findPedidosUsuario(Usuario usuario);
}
