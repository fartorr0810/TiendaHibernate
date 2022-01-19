package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
import com.example.demo.service.PedidoLineaServiceDB;
import com.example.demo.service.PedidoServiceDB;
import com.example.demo.service.ProductoServiceDB;
import com.example.demo.service.UsuarioServiceDB;

@Controller
public class UsuarioController {
	@Autowired
	private HttpSession sesion;
	@Autowired
	private UsuarioServiceDB servicioUsuario;
	@Autowired
	private ProductoServiceDB servicioProducto;
	@Autowired
	private PedidoServiceDB servicioPedido;
	@Autowired
	private PedidoLineaServiceDB servicioPedidoLinea;
	
	private static final String USUARIO="usuario";
	private static final String REDIRECTLOGIN="redirect:/login";
	private static final String PEDIDO="pedido";
	private static final String LISTAPEDIDOS="listapedidos";


	
	@GetMapping({"/","/login"})
	public String cargarUsuarios(Model model) {
		model.addAttribute("listausuarios", servicioUsuario.findAll());
		model.addAttribute(USUARIO,new Usuario());
		return "login";
	}
	@PostMapping({"/login/submit"})
	public String comprobarUserConPassword(@Valid @ModelAttribute("usuario") Usuario usuario,
			BindingResult bindingResult) {
		String direccion="";
		boolean error=bindingResult.hasErrors();
		if (servicioUsuario.findUser(usuario) && !error) {		
			sesion.setAttribute(USUARIO, this.servicioUsuario.buscarUsuarioEnRep(usuario));
			direccion="seleccion";
		}else {
			return REDIRECTLOGIN;
		}
		return direccion;
		
	}
	@GetMapping({"/seleccion/crearpedido"})
	public String crearPedido(Model model) {
		if (sesion.getAttribute(USUARIO)==null || sesion.isNew()) {
			return REDIRECTLOGIN;
		}		
		model.addAttribute("listaproductos",servicioProducto.findAll());

		return "crearpedido";
	}
	@PostMapping({"/crearpedido/submit"})
	public String crearPedidoProcesado(Model model,
			@RequestParam(name="numeroproducto")Integer[] cantidades)
			{
	
		String direccion="";
		boolean vacio=true;
		for (int i = 0; i < cantidades.length; i++) {
			if (cantidades[i]!=null) {
				vacio=false;	
			}
		}
		if (vacio) {
			direccion="redirect:/seleccion/crearpedido";
		}else {
			model.addAttribute(PEDIDO,new Pedido());
			Pedido ped=(Pedido) model.getAttribute(PEDIDO);
			this.servicioPedido.crearLinea(cantidades, ped);
			direccion="redirect:/resumenpedido/"+ped.getId().toString();
		}
		return direccion;
		}
	
	@GetMapping({"resumenpedido/{id}"})
	public String finalizarpedido(Model model,
			@PathVariable Integer id){
		Pedido ped=servicioPedido.findById(id);
		model.addAttribute("lineas",ped.getListaproductos());
		model.addAttribute(USUARIO,sesion.getAttribute(USUARIO));
		model.addAttribute("idpedido",id);
		model.addAttribute(PEDIDO,ped);
		return "resumenpedido";
	}
	
	
	
	@PostMapping({"seleccion/listado"})
	public String listarPedidos(Model model, 
			@RequestParam(required=true,value="tipoenvio") String tipoEnvio,
			@RequestParam(required=false,value="email") String email,
			@RequestParam(required=false,value="telefono") String phone,
			@RequestParam(required=false,value="direccion") String direccion,
			@RequestParam(required=true,value="idpedido") Integer id
			) {
		
		String direccionreturn="";
		if ("".equals(email) || "".equals(phone) || "".equals(direccion)) {
			direccionreturn="redirect:/resumenpedido";
		}else {
			Usuario usuario=(Usuario) sesion.getAttribute(USUARIO);
			Pedido ped=servicioPedido.findById(id);
			ped.setDireccionentrega(direccion);
			ped.setEmailcontacto(email);
			ped.setTelefonopedido(phone);
			ped.setTipopedido(tipoEnvio);
			usuario.addPedido(ped);
			this.servicioPedido.terminarPedido(usuario, ped);
			
			model.addAttribute(LISTAPEDIDOS,servicioPedido.findPedidosUsuario((Usuario) sesion.getAttribute(USUARIO)));
			direccionreturn="redirect:/seleccion/listado";
		}
		return direccionreturn;
	}
	@GetMapping({"seleccion/listado"})
	public String listadoDePedidos(Model model) {
		if (sesion.getAttribute(USUARIO)==null) {
			return REDIRECTLOGIN;			
		}
		model.addAttribute(LISTAPEDIDOS,servicioPedido.findPedidosUsuario((Usuario) sesion.getAttribute(USUARIO)));
		return LISTAPEDIDOS;
	}
	
	//Editar pedidos
	@GetMapping({"/pedido/editarpedido/{idpedido}"})
	public String editarPedido(@PathVariable Integer idpedido,Model model) {
		String direccion="";
		if (sesion.getAttribute(USUARIO)==null) {
			direccion=REDIRECTLOGIN;
		}else {
			Pedido pedido=this.servicioPedido.findById(idpedido);
			model.addAttribute(PEDIDO,pedido);
			model.addAttribute("listaproductos",this.servicioProducto.findAll());
			model.addAttribute("cantidades",this.servicioPedidoLinea.findById(idpedido));
			direccion="editarpedido";
		}
		return direccion;
	}
	/**
	 * Metodo para editar pedido, al momento de submit llama al metodo edit y recibe los parametros de id
	 * , el usuario y las nuevas cantidades de cada producto
	 * @param cantidades cantidad de productos de cada uno
	 * @param idpedido id del pedido que se va editar
	 * @return te redirecciona a la lista de pedidos del usuario
	 */
	@PostMapping({"/editarpedido/submit"})
	public String editarPedidoFinalizar(@RequestParam(required=false,value="cantidades")Integer[] cantidades,
			@RequestParam(required=false,value="idpedido")Integer idpedido, Model model) {
		//this.serviciopedido.editarPedido(idpedido, (Usuario) sesion.getAttribute(USUARIO), cantidades);
		boolean vacio=true;
		String direccionreturn;
		for (int i = 0; i < cantidades.length; i++) {
			if (cantidades[i]!=null) {
				vacio=false;	
			}
		}
		if (vacio) {
			direccionreturn="redirect:/seleccion/listado";
		}else {
			Usuario usuario=(Usuario) sesion.getAttribute("usuario");
			this.servicioUsuario.edit(usuario);
			Pedido ped=this.servicioPedido.findById(idpedido);
			ped=this.servicioPedido.updatePedido(usuario,idpedido,cantidades);
			this.servicioPedido.edit(ped);
		}
		return "redirect:/seleccion/listado";
	}
	
	@GetMapping({"/borrarpedido/{id}"})
	public String eliminarPedido(@PathVariable Integer id) {
		String direccionreturn;
		if (sesion.getAttribute(USUARIO)==null) {
			direccionreturn=REDIRECTLOGIN;
		}else {
			Usuario usuario=(Usuario) sesion.getAttribute("usuario");
			Pedido pedido=this.servicioPedido.findById(id);
			usuario.getListapedidios().remove(pedido);
			this.servicioUsuario.edit(usuario);
			this.servicioPedido.borrar(pedido);
		}
		return "redirect:/seleccion/listado";
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Metodo que se dispara al cerrar sesion y nos invalida la sesio, tras ello nos envia
	 * a al login.
	 * @return te devuelve al login
	 */
	@PostMapping({"/login/logout"})
	public String cerrarSesion(Model model) {
		sesion.invalidate();
		return REDIRECTLOGIN;
	}
}
