package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.PedidoLinea;

public interface PedidoLineaService {
	public PedidoLinea add(PedidoLinea p);
	public List<PedidoLinea> findAll();
	public Optional<PedidoLinea> findById(Integer id);
}
