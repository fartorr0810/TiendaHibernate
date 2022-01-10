package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Pedido;

public interface PedidoService {
	public Pedido add(Pedido p);
	public List<Pedido> findAll();
	public Pedido remove(int idpedido);
	public Pedido edit(Pedido pedido);
}
