package com.libreria.db.persona.controller;

import com.libreria.db.persona.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService; 
	
	@GetMapping("/list")
	public String listarUsuarios(Model model, @RequestParam(required = false) String q) {
		if (q != null) {
			model.addAttribute("usuarios", usuarioService.findAllByQ(q));
		} else {
			model.addAttribute("usuarios", usuarioService.listAll());
		}
		return "usuario-list";
	}

}
