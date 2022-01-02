package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Producto;

public class ProductoServiceMemory implements ProductoService{

	private List<Producto> repositorio = new ArrayList<>();
	
	@Override
	public Producto add(Producto p) {
		repositorio.add(p);
		return p;
	}

	@Override
	public List<Producto> findAll() {
		return repositorio;
	}

}
