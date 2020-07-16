package com.levi9.code9.authorservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.levi9.code9.authorservice.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	
}
