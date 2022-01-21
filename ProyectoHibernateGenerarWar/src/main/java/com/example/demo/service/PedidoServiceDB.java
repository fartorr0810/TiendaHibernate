package com.example.demo.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
import com.example.demo.model.PedidoLinea;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.PedidoLineaRepository;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.UsuarioRepository;
@Primary
@Service("pedidoServiceDB")
public class PedidoServiceDB implements PedidoService {
	//Cableamos los repositorios necesarios para trabajar con los pedidos junto a los servicios.
	@Autowired
	PedidoRepository repositorio;
	@Autowired
	ProductoRepository repositorioproductos;
	@Autowired
	PedidoLineaRepository repositorioPedidoLinea;
	@Autowired
	PedidoLineaServiceDB servicioPedidoLinea;
	@Autowired
	UsuarioServiceDB servicioUsuario;
	@Autowired
	UsuarioRepository repositoriousuarios;
	/**
	 * Persiste en el repositorio el pedido pasado
	 */
	@Override
	public Pedido add(Pedido p) {
		return repositorio.save(p);
	}
	/**
	 * Recibe un pedido, lo persiste y devuelve la id de esta.
	 * @param p
	 * @return nos devuelve la id del pedido
	 */
	public Integer addNumber(Pedido p) {
		repositorio.save(p);
		return p.getId();
	}
	/**
	 * Crea un objeto pedido
	 * @return devuelve un objeto pedido
	 */
	public Pedido crearPedidoVacio() {
		return new Pedido();
	}
	/**
	 * Devueve todos los pedidos que existen el repositorio
	 * @return Devuelve pedido.
	 */
	@Override
	public List<Pedido> findAll() {
		return repositorio.findAll();
	}
	/**
	 * Metodo que eliminamos del repositorio un pedido pasandole la id.
	 */
	@Override
	public boolean remove(int idpedido) {		
		repositorio.deleteById(idpedido);
		return true;
	}
	/**
	 * Metodo edit donde se usa para sobreescribir un pedido en la base de datos
	 */
	@Override
	public Pedido edit(Pedido pedido) {
		return repositorio.save(pedido);
	}
	/**
	 * Metodo que recibe un usuario y un pedido, persiste el pedido en la base de datos,
	 * tras ello mete en la base de datos todas sus lineas y tras ello mete el pedido en el usuario
	 * indicado.
	 * @param usuario 
	 * @param ped
	 * 
	 */
	public void terminarPedido(Usuario usuario,Pedido ped) {
		repositorio.save(ped);
		for (int i = 0; i < ped.getListaproductos().size(); i++) {
			this.servicioPedidoLinea.add(ped.getListaproductos().get(i));			
		}
		this.servicioUsuario.addPedido(usuario, ped);

	}
	/**
	 * Devuelve una lista de pedidos de un usuario especifico
	 * @return devuelve una lista especifica.
	 */
	@Override
	public List<Pedido> findPedidosUsuario(Usuario usuario) {
		List<Pedido> pedidos;
		pedidos=usuario.getListapedidios();
		return pedidos;
	}
	/**
	 * Metodo donde crearemos las lineas, en el se persiste primero el pedido,
	 * tras ello recorre las cantidades de los productos, obtiene el producto por la i
	 * y le pasamos el pedido al que pertenece esa linea , tras esto se persiste la linea y al pedido
	 * se le anade la linea
	 * @param cantidades
	 * @param nuevopedido
	 * @return devuelve el pedido con las lineas creadas.
	 */
	public Pedido crearLinea(Integer [] cantidades,Pedido nuevopedido) {
		List<Producto> productos=repositorioproductos.findAll();
		repositorio.save(nuevopedido);
		for (int i = 0; i < cantidades.length; i++) {
			PedidoLinea linea=new PedidoLinea(cantidades[i],productos.get(i),nuevopedido);
			servicioPedidoLinea.add(linea);
			nuevopedido.addLinea(linea);
		}
		return nuevopedido;
	}
	/**
	 * Metodo que busca en la base de datos un pedido por id, si no lo encuentra
	 * devuelve null
	 * @return Devuelve un pedido o null. 
	 */
	@Override
	public Pedido findById(Integer id) {
		return repositorio.findById(id).orElse(null);
	}
	/**
	 * Metodo en el que editamos la cantidad  de los productos en un pedido.
	 * Recorre la la lista de productos del pedido y con la i recorremos las cantidades
	 * asignando las cantidades de cada producto.
	 * @param pedd pedido
	 * @param cantidades array de cantidades
	 * @return Nos devuelve el pedido editado
	 */
	public Pedido updatePedido(Pedido pedd,Integer[] cantidades) {
		int i=0;
		Iterator<PedidoLinea> it = pedd.getListaproductos().iterator();
		while(it.hasNext()) {
				PedidoLinea linea=it.next();
				linea.setCantidad(cantidades[i]);
				i=i+1;
		}
		return pedd;
	}
	/**
	 * Metodo que recibe un pedido, primero borra las lineas de pedido y tras ello elimina el pedido
	 * @param e pedido.
	 */
	public void borrar(Pedido e) {
		Iterator<PedidoLinea> it = e.getListaproductos().iterator();
		while(it.hasNext()) {
			PedidoLinea linea = it.next();
			this.repositorioPedidoLinea.delete(linea);
		}
		repositorio.delete(e);
	}
}
