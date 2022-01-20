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
	//Declaracion de la sesion y servicios
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
	//String Literales
	private static final String USUARIO="usuario";
	private static final String REDIRECTLOGIN="redirect:/login";
	private static final String PEDIDO="pedido";
	private static final String LISTAPEDIDOS="listapedidos";
	private static final String REDIRECTLISTAPEDIDOS="redirect:/seleccion/listado";


	/**
	 * Muestra al acceder a la ruta raiz del proyecto o a /login la pantalla de logueo
	 * @param model Anadimos el usuario al que se le van a asignar los datos del formulario y 
	 * la lista de los usuarios
	 * @return Nos da la vista del login
	 */
	@GetMapping({"/","/login"})
	public String cargarUsuarios(Model model) {
		model.addAttribute("listausuarios", servicioUsuario.findAll());
		model.addAttribute(USUARIO,new Usuario());
		return "login";
	}
	/**
	 * Metodo Post que se ejecuta al dar click a loguear, en el  comprobamos que no existe errores
	 * al introducir el usuario y su el usuario existe en la base de datos con el metodo del servicio de usuario
	 * si existe, al usuario lo metemos en la sesion y si esta bien, nos manda a la pagina de
	 * seleccion o en caso contrario, de nuevo al get del login.
	 * @param usuario usuario que tiene los datos y que comprobamos que coincide con la BD
	 * @param bindingResult objeto para comprobar errores en el formulario
	 * @return si esta bien , al apartado de seleccion, si no, al login
	 */
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
	/**
	 * Comprueba que existe una sesion activa,en caso de que no , nos manda al login, si esta bien
	 * nos manda a crear pedido y cargamos en la vista todos los productos disponibles.
	 * @param model anadimos la lista de productos
	 * @return devolvemos la vista de crearpedido
	 */
	@GetMapping({"/seleccion/crearpedido"})
	public String crearPedido(Model model) {
		if (sesion.getAttribute(USUARIO)==null || sesion.isNew()) {
			return REDIRECTLOGIN;
		}		
		model.addAttribute("listaproductos",servicioProducto.findAll());

		return "crearpedido";
	}
	/**
	 * Metodo que recoge las cantidades introducidas en la pantalla de seleccion de unidades que queremos
	 * tras ello comprobamos que el carrito esta vacio o no, si esta vacio, nos quedamos en la misma pagina
	 * si anadimos algo, obtenemos el usuario , y vemos que la sesion es correcta , creamos un pedido vacio
	 * Tras ello llamamos al metodo crear lineas en el que le pasamos el pedido vacio y las cantidades
	 * tras ello se asignan al objeto ped, conseguimos la id en una variable para pasarsela posteriormente
	 * en la url para seguir trabajando el pedido en la siguiente vista
	 * @param model anadimos el usuario
	 * @param ped Objeto pedido
	 * @param idpedido Identificacion del pedido
	 * @param cantidades Array con las cantidades de los productos
	 * @return si esta bien, nos manda a la siguiente pantalla, si no hay cantidades, nos quedamos en la misma
	 * si no tenemos sesion, al login.
	 */
	@PostMapping({"/crearpedido/submit"})
	public String crearPedidoProcesado(Model model,
			@RequestParam(name="numeroproducto")Integer[] cantidades)
			{
		String direccion="";
		boolean vacio=true;
		for (int i = 0; i < cantidades.length; i++) {
			if (cantidades[i]!=0) {
				vacio=false;	
			}
		}
		if (vacio) {
			direccion="redirect:/seleccion/crearpedido";
		}else {
			if (sesion.getAttribute(USUARIO)==null) {
				direccion=REDIRECTLOGIN;
			}else {
				Pedido ped=this.servicioPedido.crearPedidoVacio();
				ped=this.servicioPedido.crearLinea(cantidades, ped);
				Integer idpedido=this.servicioPedido.addNumber(ped);
				direccion="redirect:/resumenpedido/"+idpedido;
			}
	
		}
		return direccion;
		}
	/**
	 * Metodo get que se accede desde crear pedido y en la url viaja la id del pedido, en el carga
	 * las lineas de pedido con los datos del pedido para que el usuario las vea y el precio de las cosas
	 * Aparte en la vista se le asigna un input el cual no se puede modificar con la id del pedido
	 * para que en el post de despues se pueda recoger y tirar del pedido para asignarle el resto de parametros
	 * @param model
	 * @return Nos devuelve la vista de resumen pedido
	 */
	@GetMapping({"resumenpedido/{id}"})
	public String finalizarpedido(Model model,
			@PathVariable Integer id){
		String direccionreturn="";
		if (sesion.getAttribute(USUARIO)==null || sesion.isNew()) {
			direccionreturn= REDIRECTLOGIN;
		}else {
			Pedido ped=servicioPedido.findById(id);
			model.addAttribute("lineas",ped.getListaproductos());
			model.addAttribute(USUARIO,sesion.getAttribute(USUARIO));
			model.addAttribute("idpedido",id);
			model.addAttribute(PEDIDO,ped);
			direccionreturn="resumenpedido";
		}
		return direccionreturn;
	}
	/**
	 * Metodo en el que  primero se ve que los datos no estan vacio, en caso de que algun campo
	 * este vacio, nos manda al login. Si no continuamos obtenemos el usuario de la sesion y
	 * El pedido anadido anteriormente a la base de datos que se encuentra solo con las lineas
	 * la fecha de creacion(obtenido del parametro que pasabamos anteriormente por la vista con la id
	 * . Despues al pedido le hacemos set de los parametros que se recogen con RequestParam
	 * y editamos , es decir sobreescribimos el pedido con las lineas y los datos correspondientes
	 * ademas de actualizar por supuesto al usuario.
	 * @param tipoEnvio
	 * @param email
	 * @param phone
	 * @param direccion
	 * @param id id del pedido
	 * @return si es correcto ,nos manda a la lista de pedidos, si no , nos quedamos en la misma pagina
	 */
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
			direccionreturn="redirect:/resumenpedido/"+id;
		}else {
			Usuario usuario=(Usuario) sesion.getAttribute(USUARIO);
			Pedido ped=servicioPedido.findById(id);
			ped.setDireccionentrega(direccion);
			ped.setEmailcontacto(email);
			ped.setTelefonopedido(phone);
			ped.setTipopedido(tipoEnvio);
			usuario.addPedido(ped);
			this.servicioPedido.edit(ped);
			this.servicioUsuario.edit(usuario);
			model.addAttribute(LISTAPEDIDOS,servicioPedido.findPedidosUsuario((Usuario) sesion.getAttribute(USUARIO)));
			direccionreturn=REDIRECTLISTAPEDIDOS;
		}
		return direccionreturn;
	}
	/**
	 * Metodo get donde entramos al listado de pedidos existentes para ver los pedidos realizados
	 * por el usuario especifico. 
	 * @param model
	 * @return Si no existe el usuario en sesio, para el login, si no, buscamos
	 * los pedidos del usaurio en especifico.
	 */
	@GetMapping({"seleccion/listado"})
	public String listadoDePedidos(Model model) {
		Usuario usuario=(Usuario) sesion.getAttribute(USUARIO);
		if (sesion.getAttribute(USUARIO)==null) {
			return REDIRECTLOGIN;
		}else {
			model.addAttribute(LISTAPEDIDOS,servicioPedido.findPedidosUsuario(usuario));
		}
		return LISTAPEDIDOS;
	}
	
	/**
	 * Metodo en el que se accede cuando desde la vista de lista de pedidos, hacemos click en editar
	 * Este recoge de la url la id, y en el buscaremos la id del pedido en la base de datos
	 * y posteriormente lo mostraremos, donde se cargara las cantidades de los productos
	 * para que el usuario pueda modificar las cantidades.
	 * @param idpedido id del pedido
	 * @return Si no existe usuario en sesion , para el login. si esta bien , nos muestra
	 * la pantalla de edicion. 
	 */
	@GetMapping({"/pedido/editarpedido/{idpedido}"})
	public String editarPedido(@PathVariable Integer idpedido,Model model) {
		String direccion="";
		if (sesion.getAttribute(USUARIO)==null) {
			direccion=REDIRECTLOGIN;
		}else {
			Pedido pedido=this.servicioPedido.findById(idpedido);
			model.addAttribute(USUARIO,USUARIO);
			model.addAttribute(PEDIDO,pedido);
			model.addAttribute("listaproductos",this.servicioProducto.findAll());
			direccion="editarpedido";
		}
		return direccion;
	}
	/**
	 * Metodo post donde se recogen las cantidades y la id del pedido, en este metodo se comprueba el usuario,
	 * si no existe en sesion nos manda para la misma pantalla.
	 * En caso contrario, editaremos en la base de datos el usuario, obtenemos el pedido por la id
	 * pedido , luego ejecuta el metodo update que recorre las lineas y actualiza las cantidad de 
	 * cada producto y por ultimo edita el pedido en base de dato.
	 * @param cantidades cantidad de productos de cada uno
	 * @param idpedido id del pedido que se va editar
	 * @return te redirecciona a la lista de pedidos del usuario
	 */
	@PostMapping({"/editarpedido/submit"})
	public String editarPedidoFinalizar(@RequestParam(required=false,value="cantidades")Integer[] cantidades,
			@RequestParam(required=false,value="idpedido")Integer idpedido, Model model) {
		boolean vacio=true;
		String direccionreturn;
		for (int i = 0; i < cantidades.length; i++) {
			if (cantidades[i]!=null) {
				vacio=false;	
			}
		}
		if (vacio) {
			direccionreturn=REDIRECTLISTAPEDIDOS;
		}else {
			Usuario usuario=(Usuario) sesion.getAttribute(USUARIO);
			this.servicioUsuario.edit(usuario);
			Pedido ped=this.servicioPedido.findById(idpedido);
			ped=this.servicioPedido.updatePedido(ped,cantidades);
			this.servicioPedido.edit(ped);
		}
		return REDIRECTLISTAPEDIDOS;
	}
	/**
	 * Metodo para eliminar pedido , recibe la id del pedido desde el boton para eliminar pedido.
	 * Comprobamos que existe usuario como en el resto de metodos.
	 * En el borramos de memoria el pedido indicado y posteriormente borramos primero las lineas
	 * despues la relacion entre usuario y pedido y por ultimo el pedido en si de forma manual porque
	 * los delete en cascada no funciona.
	 * @return Nos devuelve la misma vista de lista de pedidos.
	 *
	 */
	@GetMapping({"/borrarpedido/{id}"})
	public String eliminarPedido(@PathVariable Integer id) {
		String direccionreturn;
		if (sesion.getAttribute(USUARIO)==null) {
			direccionreturn=REDIRECTLOGIN;
		}else {
			Usuario usuario=(Usuario) sesion.getAttribute(USUARIO);
			Pedido pedido=this.servicioPedido.findById(id);
			usuario.getListapedidios().remove(pedido);
			this.servicioUsuario.edit(usuario);
			this.servicioPedido.borrar(pedido);
			direccionreturn=REDIRECTLISTAPEDIDOS;
		}
		return direccionreturn;
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
