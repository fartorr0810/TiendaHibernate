package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.PedidoLinea;
//Repositorio PedidoLinea
public interface PedidoLineaRepository extends JpaRepository<PedidoLinea, Integer> {

}
