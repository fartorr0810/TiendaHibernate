package com.example.demo.service;

import java.util.List;
import java.util.Optional;

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
	public Optional<PedidoLinea> BuscarById(Pedido ped){
		Optional<PedidoLinea> linea=findById(ped.getId());
		return linea;
	}

	@Override
	public Optional<PedidoLinea> findById(Integer id) {
		return repositorio.findById(id);
	}


}
