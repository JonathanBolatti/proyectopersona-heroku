package com.libreria.db.persona.repositories;

import com.libreria.db.persona.entity.Persona;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {

	@Query("SELECT p FROM Persona p WHERE p.nombre LIKE :q or p.ciudad.nombre LIKE :q")
	List<Persona> findAllByQ(@Param("q") String q);

	@Query("SELECT p FROM Persona p WHERE p.ciudad.nombre = :q")
	List<Persona> findAllByCiudad(@Param("q") String q);

	@Query("SELECT p FROM Persona p WHERE p.dni = :dni")
	Persona findByDni(@Param("dni") String dni);

}
