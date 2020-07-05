package com.levi9.code9.authservice.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long _id;

	@NotBlank(message = "First name cannot be blank. This is required field.")
	@Size(max = 30, message = "Length of the first name must be between 1 and 30.")
	@Column(name = "first_name")
	private String _firstName;

	@NotBlank(message = "Last name cannot be blank. This is required field.")
	@Size(max = 30, message = "Length of the last name must be between 1 and 30.")
	@Column(name = "last_name")
	private String _lastName;

	@NotBlank(message = "Username cannot be blank. This is required field.")
	@Size(max = 50, message = "Length of the last name must be between 1 and 50.")
	@Column(name = "username", unique = true)
	private String _username;

	@NotBlank(message = "Email cannot be blank. This is required field.")
	@Email(message = "Invalid email format.")
	@Column(name = "email", unique = true)
	private String _email;

	@Size(min = 8, max = 50, message = "Length of the last name must be between 1 and 50.")
	@Column(name = "password")
	private String _password;

	@Column(name = "gender")
	@Enumerated(value = EnumType.ORDINAL)
	@Builder.Default
	private Gender _genderGender = Gender.PREFER_NOT_TO_SAY;

	@CreatedDate
	@Column(name = "created_at")
	private Instant _createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Instant _updatedAt;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_permission", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "permission_id") })
	private List<Permission> _permissions;

	public List<String> getRoles() {
		List<String> roles = new ArrayList<>();
		for (Permission permission : getPermissions()) {
			roles.add(permission.getAuthority());
		}
		return roles;
	}

	/* data that can be added - picture, */

}