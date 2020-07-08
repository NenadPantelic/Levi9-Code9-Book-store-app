package com.levi9.code9.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.levi9.code9.userservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findUserByUsername(String username);
}
