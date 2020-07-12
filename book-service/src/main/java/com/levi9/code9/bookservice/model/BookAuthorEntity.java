package com.levi9.code9.bookservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

//@Table//(uniqueConstraints = { @UniqueConstraint(columnNames = { "book_id", "author_id" }) })
public class BookAuthorEntity {
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "author_id", nullable = false)
	private Long _authorId;
}
