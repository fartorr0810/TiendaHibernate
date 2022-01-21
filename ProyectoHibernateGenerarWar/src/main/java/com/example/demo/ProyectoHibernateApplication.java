package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.UsuarioRepository;

@SpringBootApplication
@Configuration
public class ProyectoHibernateApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoHibernateApplication.class, args);
	}
	/**
	 * Metodo para precargar usuarios y productos en la base de datos.
	 */
	@Bean
	CommandLineRunner initData(UsuarioRepository repositoriousuarios,ProductoRepository repositorioproductos) {
		return args-> {
			repositoriousuarios.saveAll(Arrays.asList(new Usuario("fartorr0810", "Usuario", "C/San Isidro Labrador N 6", "frankarroyop@gmail.com","608839891",null),
					new Usuario("Jorge", "profe12", "C/Sevilla N 2", "jorgerodriguezchacon@iesjacaranda.es","658172123",null),
					new Usuario("Miriam", "anuelbrr", "C/Santa Ana N 4", "miriamm@gmail.com","654234432",null)));
			repositorioproductos.saveAll(Arrays.asList(new Producto("White Label",12.0),
					new Producto("Ron Barcelo",17.0),
					new Producto("Puerto de Indicas",7.0)));
		};
	}
	
}
