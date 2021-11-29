package com.libreria.db.persona.services;

import com.libreria.db.persona.entity.Ciudad;
import com.libreria.db.persona.excepciones.WebExcepction;
import com.libreria.db.persona.repositories.CiudadRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiudadService {

	@Autowired
	private CiudadRepository ciudadRepository;

	@Autowired
	private PersonaService personaService;

	public Ciudad save(String name) {
		Ciudad ciudad = new Ciudad();
		ciudad.setNombre(name);
		return ciudadRepository.save(ciudad);
	}

	public Ciudad save(Ciudad ciudad) throws WebExcepction {
		if (ciudad.getNombre() == null || ciudad.getNombre().isEmpty()) {
			throw new WebExcepction("Error! debe ingresar el nombre de una Ciudad");
		} else {

		}
		return ciudadRepository.save(ciudad); 
	}

	public List<Ciudad> listAll() {
		return ciudadRepository.findAll();
	}

	public List<Ciudad> findAll(String q) {
		return ciudadRepository.findAll("%" + q + "%");
	}

	public Ciudad findByIdCity(Ciudad ciudad) {
		Optional<Ciudad> optional = ciudadRepository.findById(ciudad.getId());
		if (optional.isPresent()) {
			ciudad = optional.get();
		}
		return ciudad;
	}

	public Optional<Ciudad> findById(String id) {
		return ciudadRepository.findById(id);
	}

	@Transactional
	public void delete(Ciudad ciudad) {
		ciudadRepository.delete(ciudad);
	}

	@Transactional
	public void deleteById(String id) {
		Optional<Ciudad> optional = ciudadRepository.findById(id);
		if (optional.isPresent()) {
			Ciudad ciudad = optional.get();
			personaService.deleteFielCiudad(ciudad);
			ciudadRepository.delete(ciudad);

		}

	}
}
