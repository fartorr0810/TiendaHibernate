package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
@Service("usuarioServiceMemory")
public class UsuarioServiceMemory implements UsuarioService {
	
	private List<Usuario> repositorio = new ArrayList<>();
	
	@Override
	public Usuario add(Usuario user) {
		repositorio.add(user);
		return user;
	}

	@Override
	public List<Usuario> findAll() {
		return repositorio;
	}

	@Override
	public Usuario findById(Integer id) {
		Usuario user=null;
		boolean encontrado=false;
		int i=0;
		while(!encontrado && i<repositorio.size()) {
			if(repositorio.get(i).getId()==0) {
				encontrado=true;
				user=repositorio.get(i);				
			}else {
			i++;
			}
		}
		return user;
	}
	
	@Override
	public Usuario buscarUsuarioEnRep(Usuario usuario) {
		int posicion=this.repositorio.indexOf(usuario);
		if (posicion!=-1) {
			return this.repositorio.get(posicion);
		}
		return usuario;
	}

	@Override
	public boolean findUser(Usuario usuario) {
		boolean encontrado=false;
		if (repositorio.contains(usuario)) {
			encontrado=true;
		}
		return encontrado;
	}
	
	
}
