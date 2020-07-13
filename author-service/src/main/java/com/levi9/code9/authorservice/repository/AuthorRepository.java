package com.levi9.code9.authorservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.levi9.code9.authorservice.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

	@Query("SELECT author FROM Author author JOIN author._books books WHERE books._id = :bookId") 
	public List<Author>findAuthorsByBook(@Param("bookId") Long bookId);
	
	@Query("SELECT author._id FROM Author author JOIN author._books books WHERE books._id = :bookId") 
	public List<Long>findAuthorsIdsByBook(@Param("bookId") Long bookId);
}
