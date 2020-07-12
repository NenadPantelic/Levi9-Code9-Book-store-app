package com.levi9.code9.bookservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.levi9.code9.bookservice.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	public Optional<Book> findBookBy_title(String title);
	
	@Query("update Book b set b._isActive=false where b._id= :id")
	@Modifying
	public void softDelete(@Param("id") Long id);
	@Query("SELECT b FROM Book b JOIN b._genres bg WHERE bg._id = :genreId") 
	public List<Book>findBooksByGenre(@Param("genreId") Long genreId);
	
	@Query("SELECT b FROM Book b JOIN b._genres bg WHERE bg._name = :genreName") 
	public List<Book>findBooksByGenreName(@Param("genreName") String genreName);
}
