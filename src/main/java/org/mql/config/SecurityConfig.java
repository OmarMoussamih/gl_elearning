package org.mql.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mql.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	Logger logger = LogManager.getLogger(SecurityConfig.class);
	
	@Autowired 
	private MemberService memberService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**","/js/**","/fonts/**","/img/**","/layerslider/**","/scss/**","/vendor/**");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/admission","/dashboard/followedFormation").hasRole("STUDENT")
			.antMatchers("/dashboard/","/stream/**").hasAnyRole("STUDENT","TEACHER","RESPONSABLE","ADMIN")
			.antMatchers("/dashboard/formation/**").hasRole("RESPONSABLE")
			.antMatchers("/dashboard/stream/**").hasRole("TEACHER")
			.antMatchers("/dashboard/demands").hasRole("ADMIN")
			.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
			.successHandler(loginSuccessHandler())
			.failureHandler(loginFailureHandler())
			.and()
		.logout()
			.permitAll()
			.logoutSuccessUrl("/");
	
	}
	
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return	(request, response, authentication) -> response.sendRedirect("/dashboard/");
	}
	
	public AuthenticationFailureHandler loginFailureHandler() {
		return	(request, response, authentication) -> {
			request.getSession().setAttribute("flash", "Email et/ou mot de passe incorrect, veuillez réssayer!");

			response.sendRedirect("/login");
		};
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
}
