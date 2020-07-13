package com.levi9.code9.authorservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class BookEntity {
	@Id
	@Column(name = "book_id", nullable = false)
	private Long _bookId;
}
