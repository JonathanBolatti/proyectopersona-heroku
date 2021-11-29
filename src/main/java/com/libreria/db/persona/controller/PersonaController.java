package com.libreria.db.persona.controller;

import com.libreria.db.persona.entity.Persona;
import com.libreria.db.persona.services.CiudadService;
import com.libreria.db.persona.services.PersonaService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/persona")
public class PersonaController {

	@Autowired
	private PersonaService personaService;
	@Autowired
	private CiudadService ciudadService; 

	@GetMapping("/list")
	public String listarPersonas(Model model, @RequestParam(required = false) String q) {
		if (q != null) {
			model.addAttribute("personas", personaService.findAllByQ(q));
		} else {
			model.addAttribute("personas", personaService.listAll());
		}
		return "persona-list";
	}

	@GetMapping("/form")
	public String crearPersona(Model model, @RequestParam(required = false) String id) {
		if (id != null) {
			Optional<Persona> optional = personaService.findById(id);
			if (optional.isPresent()) {
				model.addAttribute("persona", optional.get());
			} else {
				return "redirect:/persona/list";
			}
		} else {
			model.addAttribute("persona", new Persona());
		}
		model.addAttribute("ciudades", ciudadService.listAll()); 
		return "persona-form";
	}

	@PostMapping("/save")
	public String guardarPersona(Model model, RedirectAttributes redirect, @ModelAttribute Persona persona) {
		try {
			personaService.save(persona);
			redirect.addFlashAttribute("success", "Formulario cargado con exito!");
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			//ante un error, los datos cargados en el formulario no se eliminarian
			model.addAttribute("persona", persona);
			model.addAttribute("ciudades", ciudadService.listAll()); 
			return "persona-form";	
		}
		return "redirect:/persona/list";
	}

	@GetMapping("/delete")
	public String eliminarPersona(@RequestParam(required = true) String id) {
		personaService.deleteById(id);
		return "redirect:/persona/list";
	}

}
