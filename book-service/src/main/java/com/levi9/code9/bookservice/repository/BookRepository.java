package com.levi9.code9.bookservice.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.levi9.code9.bookservice.model.Author;
import com.levi9.code9.bookservice.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	public Optional<Book> findBookBy_title(String title);

	@Query("UPDATE Book b SET b._active=false WHERE b._id= :id")
	@Modifying
	public void softDelete(@Param("id") Long id);
	
	@Query("UPDATE Book b SET b._active=false WHERE b = :book")
	@Modifying
	public void softDelete(@Param("book") Book book);


	@Query("SELECT b FROM Book b JOIN b._genres bg WHERE bg._id = :genreId")
	public List<Book> findBooksByGenre(@Param("genreId") Long genreId);

	@Query("SELECT b FROM Book b JOIN b._genres bg WHERE bg._name LIKE %:genreName%")
	public List<Book> findBooksByGenreName(@Param("genreName") String genreName);

	@Query("SELECT b FROM Book b WHERE b._title LIKE %:title%")
	public List<Book> findBooksByTitle(@Param("title") String title);

	@Query("SELECT b FROM Book b JOIN b._authors author WHERE author._id = :authorId")
	public List<Book> findBooksByAuthorId(@Param("authorId") Long authorId);


}
