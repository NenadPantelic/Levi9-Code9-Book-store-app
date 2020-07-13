package com.levi9.code9.authorservice.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "first_name", "last_name" }) })
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long _id;

	@NotBlank(message = "Author's first name must be provided.")
	@Size(max = 255, message = "Length of the author's last name must be between 1 and 255.")
	@Column(name = "first_name", nullable = false)
	private String _firstName;

	@NotBlank(message = "Author's last name must be provided.")
	@Size(max = 255, message = "Length of the author's last name must be between 1 and 255.")
	@Column(name = "last_name", nullable = false)
	private String _lastName;

	@Size(max = 500, message = "Resume text is too long. It cannot be longer than 500 characters.")
	@Column(name = "resume")
	private String _resume;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "author_book", joinColumns = { @JoinColumn(name = "author_id") }, inverseJoinColumns = {
			@JoinColumn(name = "book_id") })
	@Builder.Default
	private Set<BookEntity> _authors = new HashSet<BookEntity>();

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

	public void addBook(BookEntity book) {
		getAuthors().add(book);
	}
}
