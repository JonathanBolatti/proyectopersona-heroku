package com.libreria.db.persona.services;

import com.libreria.db.enums.Role;
import com.libreria.db.persona.entity.Persona;
import com.libreria.db.persona.entity.Usuario;
import com.libreria.db.persona.excepciones.WebExcepction;
import com.libreria.db.persona.repositories.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired //para llamar al usuarioRepository
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PersonaService personaService;

	@Transactional
	public Usuario save(String username, String password, String password2, String dni) throws WebExcepction {
		Usuario usuario = new Usuario();

		if (dni.isEmpty() || dni == null) {
			throw new WebExcepction("El DNI no puede estar vacio");
		}
		//se utiliza para poder validar la persona
		Persona persona = personaService.findByDni(dni);
		if (persona == null) {
			throw new WebExcepction("Dni Inexistente, compruebe nuevamente!");
		}
		if (username.isEmpty() || username == null) {
			throw new WebExcepction("El nombre de usuario no puede estar vacio");
		}
		if (findByUsername(username) != null) {
			throw new WebExcepction("El nombre de usuario ya existe!");
		}
		if (password.isEmpty() || password == null || password2.isEmpty() || password2 == null) {
			throw new WebExcepction("La contraseña no pueden estar vacias");
		}
		if (!password.equals(password2)) {
			throw new WebExcepction("Las contraseñas deben ser iguales");
		}
		/*Encoder -> para encriptar la contraseña*/
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usuario.setId(persona.getId());
		usuario.setNombre(persona.getNombre());
		usuario.setApellido(persona.getApellido());
		usuario.setEdad(persona.getEdad());
		usuario.setCiudad(persona.getCiudad());
		usuario.setDni(persona.getDni());
		usuario.setUserName(username);
		usuario.setPassword(encoder.encode(password));
		usuario.setRol(Role.USER);

		personaService.delete(persona);
		return usuarioRepository.save(usuario);
	}

	public Usuario findByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

	public List<Usuario> listAll() {
		return usuarioRepository.findAll();
	}

	public List<Usuario> findAllByQ(String q) {
		return usuarioRepository.findAllByQ("%" + q + "%");
		//return usuarioRepository.findAll(); 
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			Usuario usuario = usuarioRepository.findByUsername(username);
			List<GrantedAuthority> permisos = new ArrayList<>();
			permisos.add(new SimpleGrantedAuthority("ROLE_"+usuario.getRol()));
		
			/*para mostrar datos del usuario logueado*/
			GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO"); 
			permisos.add(p1); 
			ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes(); 
			HttpSession session = attr.getRequest().getSession(true); 
			session.setAttribute("usuariosession", usuario);
			
			User user = new User(username, usuario.getPassword(), permisos);
			return user; 
		} catch (Exception e) {
			throw new UsernameNotFoundException("El usuario no existe! ");
		}
	}

}
