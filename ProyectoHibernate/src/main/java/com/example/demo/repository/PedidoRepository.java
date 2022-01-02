package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Pedido;
//En el ejemplo ponia Long pero puse Integer.

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
