package fr.eni.projetEnchere.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	private UserDetailsService service;
	
	HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.ALL));


	public WebSecurityConfig(UserDetailsService service) {
		super();
		this.service = service;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/images/**","/css/**","/login","/","/register","/home","/encheres").permitAll()
				.requestMatchers("/article/loadArticles", "/article/searchArticles").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.defaultSuccessUrl("/home", true)
				.permitAll()
			)
			.logout((logout) -> logout
					.deleteCookies("JSESSIONID")
					.clearAuthentication(true)
					.invalidateHttpSession(true)
					.addLogoutHandler(clearSiteData)
	                .logoutUrl("/logout") // URL de déconnexion
	                .logoutSuccessUrl("/home") // Redirection après déconnexion
	                .permitAll() // Accessible sans authentification        
	            )
			.rememberMe()
	        	.key("uniqueAndSecretKey")
	        	.tokenValiditySeconds(86400);
		//logger.debug("login");
		http.build();
		return http.getOrBuild();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		// NoOpPasswordEncoder si on ne veut pas chiffrer les mots de passe !!
		// return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user =
//			 User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("password")
//				.roles("USER")
//				.build();
//
//		return new InMemoryUserDetailsManager(user);
//	}
}
