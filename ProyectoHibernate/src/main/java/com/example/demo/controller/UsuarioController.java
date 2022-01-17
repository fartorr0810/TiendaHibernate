package com.example.demo.controller;

import java.util.List;

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
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.service.PedidoService;
import com.example.demo.service.PedidoServiceDB;
import com.example.demo.service.ProductoService;
import com.example.demo.service.ProductoServiceDB;
import com.example.demo.service.UsuarioService;
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
	@GetMapping({"/","/login"})
	public String cargarUsuarios(Model model) {
		model.addAttribute("listausuarios", servicioUsuario.findAll());
		model.addAttribute("usuario",new Usuario());
		return "login";
	}
	@PostMapping({"/login/submit"})
	public String comprobarUserConPassword(@Valid @ModelAttribute("usuario") Usuario usuario,
			BindingResult bindingResult) {
		String direccion="";
		boolean error=bindingResult.hasErrors();
		if (servicioUsuario.findUser(usuario) && !error) {		
			sesion.setAttribute("usuario", this.servicioUsuario.buscarUsuarioEnRep(usuario));
			direccion="seleccion";
		}else {
			return "redirect:/login";
		}
		return direccion;
		
	}
	@GetMapping({"/seleccion/crearpedido"})
	public String crearPedido(Model model) {
		if (sesion.getAttribute("usuario")==null || sesion.isNew()) {
			return "redirect:/login";
		}		
		model.addAttribute("listaproductos",servicioProducto.findAll());

		return "crearpedido";
	}
	//IGNORE
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
			model.addAttribute("pedido",new Pedido());
			Pedido ped=(Pedido) model.getAttribute("pedido");
			model.addAttribute("idpedido",ped.getId());
			this.servicioPedido.crearLinea(cantidades, ped);
			direccion="redirect:/resumenpedido/"+ped.getId().toString();
		}
		return direccion;
		}
	
	//TODO
	@GetMapping({"resumenpedido/{id}"})
	public String finalizarpedido(Model model,
			@PathVariable Integer id){
		Pedido ped=servicioPedido.findById(id);
		model.addAttribute("lineas",ped.getListaproductos());
		model.addAttribute("usuario",sesion.getAttribute("usuario"));

		
		return "resumenpedido";
	}
	
	
	
	@PostMapping({"seleccion/listado"})
	public String listarPedidos(Model model, 
			//@RequestParam(required=true,value="tipoenvio") String tipoEnvio,
			@RequestParam(required=false,value="email") String email,
			@RequestParam(required=false,value="telefono") String phone,
			@RequestParam(required=false,value="direccion") String direccion,
			@ModelAttribute("pedido") Pedido ped) {
		String direccionreturn="";
		if ("".equals(email) || "".equals(phone) || "".equals(direccion)) {
			direccionreturn="redirect:/resumenpedido";
		}else {
			
			this.servicioPedido.addPedido((Usuario) sesion.getAttribute("usuario"), "Normal",this.servicioPedido.getAux(), direccion,
					email, phone);
			model.addAttribute("listapedidos",servicioPedido.findPedidosUsuario((Usuario) sesion.getAttribute("usuario")));
			direccionreturn="redirect:/seleccion/listado";
		}
		return direccionreturn;
	}
	@GetMapping({"seleccion/listado"})
	public String finalizarPedido(Model model) {
		if (sesion.getAttribute("usuario")==null) {
			return "redirect:/login";			
		}
		model.addAttribute("listapedidos",servicioPedido.findPedidosUsuario((Usuario) sesion.getAttribute("usuario")));
		return "listapedidos";
	}
}
