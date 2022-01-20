package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
//Servicio
@Primary
@Service("productoServiceDB")
public class ProductoServiceDB implements ProductoService{
//Cableamos el repositorio
	@Autowired
	private ProductoRepository repositorio;
	/**
	 * Metodo que recibe un producto y lo guarda en la base de datos
	 * @return pedido
	 */
	@Override
	public Producto add(Producto p) {
		return repositorio.save(p);
	}
	/**
	 * Metodo que devuelve la lista de productos existentes en la base de datos.
	 * @return lista de productos existentes.
	 */
	@Override
	public List<Producto> findAll() {
		return repositorio.findAll();
	}
	
}
