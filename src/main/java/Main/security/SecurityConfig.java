package Main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import Main.model.User;
import Main.repository.UserRepository;
import Main.service.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired 
	UserRepository repo;
	@Autowired
	private AccessDeniedHandler accessDenied;
	
	@Bean
	public UserDetailsService customUserDetailsService() {
		return new CustomUserDetailsService();
	}
	
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	     
	          .withUser("admin").password("qwerty").roles("ADMIN");
	          auth.userDetailsService(customUserDetailsService());
	    } 
	@SuppressWarnings("deprecation")
	  @Bean
	  public static NoOpPasswordEncoder passwordEncoder() {
	  return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	  }
	@Override
	protected void configure(HttpSecurity http ) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/","/register","/about").permitAll()
			.antMatchers("/admin").hasAnyRole("ADMIN")
			.antMatchers("/user").hasAnyRole("USER")
			.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll().and().logout().logoutUrl("/logout")
	                .logoutSuccessUrl("/")
	                .permitAll()
		.and()
        .exceptionHandling().accessDeniedHandler(accessDenied);
}}