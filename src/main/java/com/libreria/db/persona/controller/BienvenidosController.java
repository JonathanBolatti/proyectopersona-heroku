package com.libreria.db.persona.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bienvenidos")
public class BienvenidosController {
	
		/*PreAuthorize sirve para restringir el acceso a ciertas url si el usuario no esta logueado*/
		@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
		@GetMapping("")
		public String bienvenidos() {
			return "bienvenidos";
		}
	}
