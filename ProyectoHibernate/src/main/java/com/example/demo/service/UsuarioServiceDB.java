package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
@Primary
@Service("usuarioServiceDB")
public class UsuarioServiceDB implements UsuarioService{

	@Autowired
	private UsuarioRepository repositorio;
	
	@Override
	public Usuario add(Usuario user) {
		return repositorio.save(user);
	}

	@Override
	public List<Usuario> findAll() {
		return repositorio.findAll();
	}

	@Override
	public Usuario findById(Integer id) {
		return repositorio.findById(id).orElse(null);
	}

	@Override
	public Usuario buscarUsuarioEnRep(Usuario usuario) {
		List<Usuario> listausuarios=repositorio.findAll();
		Usuario usuariocorrecto = null;
		for (int i = 0; i < listausuarios.size(); i++) {
			if (listausuarios.get(i).equals(usuario)) {
				usuariocorrecto=listausuarios.get(i);
			}
		}
		
		return usuariocorrecto;
	}
	@Override
	public boolean findUser(Usuario usuario) {
		List<Usuario> listausuarios=repositorio.findAll();
		boolean encontrado=false;
		for (int i = 0; i < listausuarios.size(); i++) {
			if (listausuarios.get(i).equals(usuario)) {
				encontrado=true;
			}
		}
		return encontrado;
	}

	
}
