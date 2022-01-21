package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Usuario;
//Interfaz con metodos genericos
public interface UsuarioService {
	public Usuario add(Usuario user);
	public List<Usuario> findAll();
	public Usuario findById(Integer id);
	public Usuario buscarUsuarioEnRep(Usuario usuario);
	public boolean findUser(Usuario usuario);
	

}
