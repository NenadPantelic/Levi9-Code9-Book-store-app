package com.levi9.code9.authservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.levi9.code9.authservice.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	public Role findBy_description(String description);
}
