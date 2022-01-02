package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Usuario;
//En el ejemplo ponia Long pero puse Integer.
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
}
