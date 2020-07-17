package com.levi9.code9.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.levi9.code9.authservice.security.AuthManager;
import com.levi9.code9.authservice.security.JwtTokenFilter;
import com.levi9.code9.authservice.security.RestAuthenticationEntryPoint;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Getter
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenFilter _tokenFilter;

	@Autowired
	private RestAuthenticationEntryPoint _authEntryPoint;

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return new AuthManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/auth/*", "**/api-docs/**", "/swagger-resources/**", "**/swagger-ui.html", "/webjars/**")
				.permitAll().antMatchers("/api/v1/**").authenticated().and().exceptionHandling()
				.authenticationEntryPoint(getAuthEntryPoint()).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(getTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		// anyRequest()
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
