package com.libreria.db.persona.services;

import com.libreria.db.persona.entity.Ciudad;
import com.libreria.db.persona.entity.Persona;
import com.libreria.db.persona.excepciones.WebExcepction;
import com.libreria.db.persona.repositories.PersonaRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {
	
	@Autowired
	private PersonaRepository personaRepository;
	@Autowired	
	private CiudadService ciudadService;	
	
	@Transactional
	public Persona save(Persona persona) throws WebExcepction {
		
		if (findByDni(persona.getDni()) != null) {
			throw new WebExcepction("Error! el Dni ya se encuentra registrado");
		}
		if (persona.getNombre().isEmpty() || persona.getNombre() == null) {
			throw new WebExcepction("Error! debe ingresar un \"Nombre\"");			
		}
		if (persona.getApellido().isEmpty() || persona.getApellido() == null) {
			throw new WebExcepction("Error! debe ingresar un \"Apellido\"");			
		}
		if (persona.getEdad() == null || persona.getEdad() < 1) {
			throw new WebExcepction("Error! debe ingresar una \"Edad\"");			
		}
		if (persona.getDni() == null || persona.getDni().isEmpty()) {
			throw new WebExcepction("Error! debe ingresar el \" N° de DNI\"");			
		}
		if (persona.getCiudad() == null) {
			throw new WebExcepction("Error! debe ingresar una \"Ciudad\"");			
		} else {
			persona.setCiudad(ciudadService.findByIdCity(persona.getCiudad()));
		}
		
		return personaRepository.save(persona);
	}
	
	public List<Persona> listAll() {
		return personaRepository.findAll();
	}
	
	public List<Persona> findAllByQ(String q) {
		return personaRepository.findAllByQ("%" + q + "%");
	}
	
	public List<Persona> listAllByCiudad(String nombre) {
		
		return personaRepository.findAll();
	}
	
	public Optional<Persona> findById(String id) {
		return personaRepository.findById(id);
	}
	
	public Persona findByDni(String dni) {
		return personaRepository.findByDni(dni);
	}
	
	@Transactional
	public void delete(Persona persona) {
		personaRepository.delete(persona);
	}
	
	@Transactional
	public void deleteFielCiudad(Ciudad ciudad) {
		List<Persona> personas = listAllByCiudad(ciudad.getNombre());		
		for (Persona persona : personas) {
			persona.setCiudad(null);
		}
		personaRepository.saveAll(personas);
	}
	
	@Transactional
	public void deleteById(String id) {
		Optional<Persona> optional = personaRepository.findById(id);
		if (optional.isPresent()) {
			personaRepository.delete(optional.get());
			
		}
	}
	
}//fin class
