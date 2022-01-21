package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
//Servicio Usuarios
@Primary
@Service("usuarioServiceDB")
public class UsuarioServiceDB implements UsuarioService{
//Cableamos lo necesario
	@Autowired
	private UsuarioRepository repositorio;
	/**
	 * Metodo que recibe un usuario y lo anade a la base de datos
	 */
	@Override
	public Usuario add(Usuario user) {
		return repositorio.save(user);
	}
	/**
	 * Metodo que devuelve una lista con todos los usuarios que existen en la base de datos
	 */
	@Override
	public List<Usuario> findAll() {
		return repositorio.findAll();
	}
	/**
	 * Metodo que busca un usuario por id en la base de dato
	 * @return nos devuelve el usuario en caso de que exista la id , si no , devuelve null
	 */
	@Override
	public Usuario findById(Integer id) {
		return repositorio.findById(id).orElse(null);
	}
	/**
	 * Metodo que recibe un usuario y busca mediante un for si encuentra o no el usuario.
	 * @return devuelve el usuario.
	 */
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
	/**
	 * Recibe un usuario y busca un usuario en el repositorio.
	 */
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
	/**
	 * Metodo que recibe un usuario, crea uno auxiliar apartir de la base de datos y obteniendo la 
	 * id exacta del usuario que pasamos ya que en algun punto JPA deja de estar enlazado con
	 * la memoria. En este metodo al auxliar le seteamos la lista de pedidos que tenia el usuario
	 * y luego persistimos el auxiliar sobreescribiendo el usuario.
	 * @param usuario usuario que le pasamos
	 * @return Devuelve el usuario
	 */
	public Usuario edit(Usuario usuario) {
		Usuario aux=findById(usuario.getId());
		aux.setListapedidios(usuario.getListapedidios());
		return repositorio.save(aux);
		
	}
	/**
	 * Metodo que recibe un usuario y un pedido, este usuario se le anade el pedido y despues
	 * persiste el usuario.
	 * @param usuario
	 * @param ped
	 */
	public void addPedido(Usuario usuario,Pedido ped) {
		usuario.addPedido(ped);
		this.repositorio.save(usuario);
	}
	
}
