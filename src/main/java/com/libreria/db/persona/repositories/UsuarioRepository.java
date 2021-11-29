package com.libreria.db.persona.repositories;

import com.libreria.db.persona.entity.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	
	@Query("select u from Usuario u where u.username = :username")
	Usuario findByUsername(@Param("username") String username);
	
	//borrar esto si no funciona
	@Query("SELECT u FROM Usuario u WHERE u.username LIKE :q")
	List<Usuario> findAllByQ(@Param("q") String q);

}
