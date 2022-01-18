package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
import com.example.demo.model.PedidoLinea;
import com.example.demo.repository.PedidoLineaRepository;


@Primary
@Service("pedidoLineaServiceDB")
public class PedidoLineaServiceDB implements PedidoLineaService {
	@Autowired
	PedidoLineaRepository repositorio;
	@Autowired
	PedidoLineaRepository repositorioPedidos;
	@Override
	public PedidoLinea add(PedidoLinea p) {
		return repositorio.save(p);
	}

	@Override
	public List<PedidoLinea> findAll() {
		return repositorio.findAll();
	}
	public List<PedidoLinea> BuscarById(Pedido ped){
		List<PedidoLinea> linea=repositorio.findById(ped.getId());
		return linea;
	}


}
