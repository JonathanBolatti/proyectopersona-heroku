package com.libreria.db.persona.repositories;

import com.libreria.db.persona.entity.Ciudad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, String> {
	
	
	@Query("SELECT c FROM Ciudad c WHERE c.nombre LIKE :q")
	List<Ciudad> findAll(@Param("q") String q); 
	
	
}
