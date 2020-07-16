package com.levi9.code9.bookservice.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "is_active=true")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long _id;

	@NotBlank(message = "Book title must be provided.")
	@Size(max = 100, message = "Length of the book title must be between 1 and 100.")
	@Column(name = "title", unique = true, nullable = false)
	private String _title;

	@NotBlank(message = "Description cannot be blank. This is required field.")
	@Column(name = "description", nullable = false)
	private String _description;

	@NotNull(message = "Book price must be provided.")
	@Column(name = "price", nullable = false)
	private BigDecimal _unitPrice;

	@NotNull(message = "Book quantity must be provided.")
	@Column(name = "quantity", nullable = false)
	private Integer _quantity;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	@Builder.Default
	private Date _createdAt = new Date();

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	@Builder.Default
	private Date _updatedAt = new Date();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "book_genre", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
			@JoinColumn(name = "genre_id") })
	@Builder.Default
	private Set<Genre> _genres = new HashSet<Genre>();

	// TODO: make a new entity - Publisher
	@Column(name = "publisher", nullable = false)
	private String _publisher;

	@Column(name = "release_date", nullable = false)
	@Builder.Default
	private LocalDate _releaseDate = LocalDate.of(2001, 1, 1);

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "book_author", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
			@JoinColumn(name = "author_id") })
	@Builder.Default
	private List<Author> _authors = new ArrayList<Author>();

	@Column(name = "is_active")
	@Builder.Default
	private Boolean _isActive = true;

	public void addGenre(Genre genre) {
		_genres.add(genre);
	}

	public void addGenres(Collection<Genre> genres) {
		_genres.addAll(genres);
	}

	public List<Long> getAuthorsIds() {
		return getAuthors().stream().map(val -> val.getId()).collect(Collectors.toList());
	}

	public void removeAuthor(Author author) {
		if (getAuthors().contains(author)) {
			getAuthors().remove(author);
		}

	}

	@PreRemove
	private void preRemove() {
		_isActive = false;
	}

}
