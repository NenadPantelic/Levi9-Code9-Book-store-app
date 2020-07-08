package com.levi9.code9.userservice.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//@Accessors(prefix = "_")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	//private Long _id;
	private Long id;


	@NotBlank(message = "First name cannot be blank. This is required field.")
	@Size(max = 30, message = "Length of the first name must be between 1 and 30.")
	@Column(name = "first_name")
	private String firstName;

	@NotBlank(message = "Last name cannot be blank. This is required field.")
	@Size(max = 30, message = "Length of the last name must be between 1 and 30.")
	@Column(name = "last_name")
	private String lastName;

	@NotBlank(message = "Username cannot be blank. This is required field.")
	@Size(max = 50, message = "Length of the last name must be between 1 and 50.")
	@Column(name = "username", unique = true)
	private String username;

	@NotBlank(message = "Email cannot be blank. This is required field.")
	@Email(message = "Invalid email format.")
	@Column(name = "email", unique = true)
	private String email;

	@Size(min = 6, max = 255, message = "Length of the password must be between 6 and 50.")
	@Column(name = "password")
	private String password;

	@Column(name = "gender")
	@Enumerated(value = EnumType.ORDINAL)
	@Builder.Default
	private Gender gender = Gender.PREFER_NOT_TO_SAY;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role", nullable = false)
	private Role role;

	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;

	@Column(name = "account_non_locked")
	private Boolean accountNonLocked;

	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;

	@Column(name = "enabled")
	private Boolean enabled;
//
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "user_role", joinColumns = { @JoinColumn (name = "user_id") },
//			inverseJoinColumns = { @JoinColumn (name = "role_id")})
//	private Set<Role> _roles;
//	
//	public List<String> getRoles() {
//		List<String> roles = new ArrayList<>();
//		for (Role role : this.permissions) {
//			roles.add(permission.getDescription());
//		}
//		return roles;
//	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(role);
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}