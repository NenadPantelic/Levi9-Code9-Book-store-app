package com.levi9.code9.bookservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.levi9.code9.bookservice.model.Genre;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
	public Genre findBy_name(String name);
}
