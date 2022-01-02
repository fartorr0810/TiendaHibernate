package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Producto;
//En el ejemplo ponia Long pero puse Integer.

public interface ProductoRepository extends JpaRepository<Producto, Integer>{

}
