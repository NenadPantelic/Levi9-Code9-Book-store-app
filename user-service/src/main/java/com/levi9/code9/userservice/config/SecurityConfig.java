package com.levi9.code9.userservice.config;

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

import com.levi9.code9.userservice.security.AuthManager;
import com.levi9.code9.userservice.security.JwtTokenFilter;
import com.levi9.code9.userservice.security.JwtTokenProvider;
import com.levi9.code9.userservice.security.RestAuthenticationEntryPoint;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Getter
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenProvider _tokenProvider;

	@Autowired
	private UserDetailsService _userDetailsService;

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
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/auth/*").permitAll().anyRequest()
				.authenticated().and().exceptionHandling().authenticationEntryPoint(_authEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(getTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// Configure in-memory authentication provider for service accounts for //
		// service inter-communication

		auth.inMemoryAuthentication().withUser("sone") //
				.password(passwordEncoder().encode("12345")) //
				.roles("ADMIN");

		// Configure DB authentication provider for user accounts
		auth.userDetailsService(getUserDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
