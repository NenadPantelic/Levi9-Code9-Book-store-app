package com.levi9.code9.authorservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.levi9.code9.authorservice.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
