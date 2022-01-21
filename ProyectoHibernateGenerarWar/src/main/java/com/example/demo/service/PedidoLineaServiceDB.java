package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.PedidoLinea;
import com.example.demo.repository.PedidoLineaRepository;

//Servicio PedidoLinea
@Primary
@Service("pedidoLineaServiceDB")
public class PedidoLineaServiceDB implements PedidoLineaService {
	@Autowired
	PedidoLineaRepository repositorio;
	@Autowired
	PedidoLineaRepository repositorioPedidos;
	/**
	 * Anade la linea de pedido a la base de datos.
	 * @return Devuelve la linea
	 */
	@Override
	public PedidoLinea add(PedidoLinea p) {
		return repositorio.save(p);
	}
	/**
	 * Busca todas las lineas de pedidos existentes
	 * @return devuelve la lista  
	 */
	@Override
	public List<PedidoLinea> findAll() {
		return repositorio.findAll();
	}

	/**
	 * Busca las lineas de pedido por id.
	 * @return devuelve la linea buscada especificamente por la id.
	 */
	@Override
	public Optional<PedidoLinea> findById(Integer id) {
		return repositorio.findById(id);
	}


}
