package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;

public interface PedidoService {
	public Pedido add(Pedido p);
	public List<Pedido> findAll();
	public boolean remove(int idpedido);
	public Pedido edit(Pedido pedido);
	public Pedido crearPedido(Integer[] cantidades);
}
