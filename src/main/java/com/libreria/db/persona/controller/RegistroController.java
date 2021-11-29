package com.libreria.db.persona.controller;

import com.libreria.db.persona.excepciones.WebExcepction;
import com.libreria.db.persona.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registro")
public class RegistroController {

	@Autowired // sirve para inyectar los servicios de la entidad
	private UsuarioService usuarioService;

	@GetMapping("") //comillas, significa que va a tomar la url del RequestMapping 
	public String registro() {
		return "registro";
	}

	@PostMapping("") //significa que va a recibir una peticion  
	public String registroSave(Model model, @RequestParam String username, @RequestParam String password, @RequestParam String password2, @RequestParam String dni) {
	
		//el metedo try-catch sirve para lanzar a la vista el error e indicarle al usuario de que se trata
		try {
			usuarioService.save(username, password, password2, dni);
			return "redirect:/";
			
		} catch (WebExcepction ex) {
			model.addAttribute("error", ex.getMessage());
			//si hay un error de contraseña, el username queda cargado en el formulario
			model.addAttribute("username",username);
			return "registro"; 
		}
	}
}
