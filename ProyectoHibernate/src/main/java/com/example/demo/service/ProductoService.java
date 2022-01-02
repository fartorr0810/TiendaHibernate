package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Producto;

public interface ProductoService {
	public Producto add(Producto user);
	public List<Producto> findAll();
}
