package com.libreria.db.persona;

import com.libreria.db.persona.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter{
	
	//La seguridad de nuestra pagina va a estar compuesta por:
	//• Autenticacion  -> un usuario que se puede logear
	//• Autorizaciones -> lo que tiene permitido cada usuario (administrador) 
	
	//•UserDatailService -> loadByUserName(metodo que vamos aplicar para que el usuario se loguee) -> usuarioServicio
	@Autowired
	private UsuarioService usuarioService;  
	
	//•Un metodo que va a configurar la autenticacion (vamos a definir los tipos de permisos)
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder()); 
	}

	//•la configuracion de las peticciones de http:// 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/*","/img/*","/js/*").permitAll()
				.and().formLogin()
					.loginPage("/login")
					.usernameParameter("username")
					.passwordParameter("password")
					.defaultSuccessUrl("/bienvenidos")//url donde va a ser redirigido el usuario al loguearse
					.loginProcessingUrl("/logincheck")
					.failureUrl("/login?error=error")
					.permitAll()
				.and().logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login?logout")
				.and().csrf().disable(); //es una petision de seguridad que no permite estar logueado en dos dispositivos diferentes al mismo tiempo. 
	}
	
	
}
