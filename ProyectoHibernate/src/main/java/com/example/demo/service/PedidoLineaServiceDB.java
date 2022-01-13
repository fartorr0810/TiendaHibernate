package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.PedidoLinea;
import com.example.demo.repository.PedidoLineaRepository;



public class PedidoLineaServiceDB implements PedidoLineaService {
	@Autowired
	PedidoLineaRepository repositorio;
	
	@Override
	public PedidoLinea add(PedidoLinea p) {
		return repositorio.save(p);
	}

	@Override
	public List<PedidoLinea> findAll() {
		return repositorio.findAll();
	}



}
