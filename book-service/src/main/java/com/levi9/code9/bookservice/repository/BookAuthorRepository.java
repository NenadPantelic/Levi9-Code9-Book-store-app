package com.levi9.code9.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.levi9.code9.bookservice.model.BookAuthor;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long>{

}
