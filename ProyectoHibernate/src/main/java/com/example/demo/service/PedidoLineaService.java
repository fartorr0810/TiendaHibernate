package com.example.demo.service;

import java.util.List;

import com.example.demo.model.PedidoLinea;

public interface PedidoLineaService {
	public PedidoLinea add(PedidoLinea p);
	public List<PedidoLinea> findAll();
}
