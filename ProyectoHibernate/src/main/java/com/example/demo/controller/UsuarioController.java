package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

@Controller
public class UsuarioController {
	@Autowired
	private HttpSession sesion;
	@Autowired
	private UsuarioService servicioUsuario;
	
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
}
