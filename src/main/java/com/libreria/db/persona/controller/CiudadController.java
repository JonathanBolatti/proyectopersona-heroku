package com.libreria.db.persona.controller;

import com.libreria.db.persona.entity.Ciudad;
import com.libreria.db.persona.services.CiudadService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ciudad")
public class CiudadController {

	@Autowired
	private CiudadService ciudadService;
	
	@GetMapping("/list")
	public String listarCiudades(Model model, @RequestParam(required = false) String q) {
		if (q != null) {
			model.addAttribute("ciudades", ciudadService.findAll(q));
		} else {
			model.addAttribute("ciudades", ciudadService.listAll());
		}
		return "ciudad-list";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/form")
	public String crearCiudad(Model model, @RequestParam(required = false) String id) {
		if (id != null) {
			Optional<Ciudad> optional = ciudadService.findById(id);
			if (optional.isPresent()) {
				model.addAttribute("ciudad", optional.get());
			} else {
				return "redirect:/ciudad/list";
			}
		} else {
			model.addAttribute("ciudad", new Ciudad());
		}
		return "ciudad-form";
	}

	@PostMapping("/save")
	public String guardarCiudad(Model model, RedirectAttributes redirect, @ModelAttribute Ciudad ciudad) {
		try {
			ciudadService.save(ciudad);
			redirect.addFlashAttribute("success", "Formulario cargado con exito!");
		} catch (Exception e) {
			redirect.addFlashAttribute("error", e.getMessage());
			return "redirect:/ciudad/form";
		}
		return "redirect:/ciudad/list";
	}

	@GetMapping("/delete")
	public String eliminarCiudad(@RequestParam(required = true) String id) {
		ciudadService.deleteById(id);
		return "redirect:/ciudad/list";
	}

}
