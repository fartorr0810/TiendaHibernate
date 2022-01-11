package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;

@Primary
@Service("productoServiceDB")
public class ProductoServiceDB implements ProductoService{

	@Autowired
	private ProductoRepository repositorio;
	
	@Override
	public Producto add(Producto p) {
		return repositorio.save(p);
	}

	@Override
	public List<Producto> findAll() {
		return repositorio.findAll();
	}
	
}
