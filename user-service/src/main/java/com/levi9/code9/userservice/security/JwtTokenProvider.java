package com.levi9.code9.userservice.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.levi9.code9.userservice.exception.InvalidJwtAuthenticationException;
import com.levi9.code9.userservice.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@Service
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:vl52rmppeu}")
	private String _secretKey;

	@Value("${security.jwt.token.expire-length:3600000}")
	private long _validityInMilliseconds; // 1h

	@Autowired
	private UserService _userService;

	@PostConstruct
	protected void init() {
		_secretKey = Base64.getEncoder().encodeToString(_secretKey.getBytes());
	}

	public String createToken(String username, String role) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("role", role);

		Date now = new Date();
		Date validity = new Date(now.getTime() + _validityInMilliseconds);
		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, _secretKey).compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = getUserService().loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private String getUsername(String token) {
		return Jwts.parser().setSigningKey(_secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(_secretKey).parseClaimsJws(token);
			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;
		} catch (SignatureException ex) {
			log.error("Invalid JWT signature");
			throw new InvalidJwtAuthenticationException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
			throw new InvalidJwtAuthenticationException("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
			throw new InvalidJwtAuthenticationException("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
			throw new InvalidJwtAuthenticationException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
			throw new InvalidJwtAuthenticationException("JWT claims string is empty.");
		}
	}
}
