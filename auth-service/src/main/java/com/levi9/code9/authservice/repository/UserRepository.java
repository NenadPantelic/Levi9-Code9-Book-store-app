package com.levi9.code9.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.levi9.code9.authservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
