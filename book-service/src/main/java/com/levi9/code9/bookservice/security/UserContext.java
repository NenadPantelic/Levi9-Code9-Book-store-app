package com.levi9.code9.bookservice.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
@AllArgsConstructor
public class UserContext {
	
	private Long _userId;
	private String _username;
	private String _token;
	private List<GrantedAuthority> _authorities;

}
