/* -----------  Data loading -------------- */

/*  1) Auth MS database */


use Code9_Auth_Service_Book_Store;
-- roles
insert into `role`(`id`, `description`) values (1, "ADMIN"), (2, "BUYER");

-- users
-- ADMIN
insert into `user`(`id`, `first_name`, `last_name`, `username`, `email`, `password`, `gender`, `created_at`) values 
(1, "Nenad" ,"Pantelic" , "sone", "pantelija@gmail.com", "$2a$10$8yWc0CpfBVQBmktAATyhxOlqpj9Sbxu9NKfN.JedG1tbsByEGd.dm", "MALE", current_timestamp());
-- BUYER
insert into `user`(`id`, `first_name`, `last_name`, `username`, `email`, `password`, `gender`, `created_at`) values 
(2, "Nenad" ,"Pantelic" , "sone_kupac", "pantelija2@gmail.com", "$2a$10$8yWc0CpfBVQBmktAATyhxOlqpj9Sbxu9NKfN.JedG1tbsByEGd.dm", "MALE", current_timestamp());

-- plain text password is 12345
-- user_role
-- ADMIN, BUYER
insert into `user_role`(`user_id`, `role_id`) values (1, 1), (2,2);


/*  2) User MS database */

use Code9_User_Service_Book_Store;

-- roles
insert into `role`(`id`, `description`) values (1, "ADMIN"), (2, "BUYER");

-- users
-- ADMIN
insert into `user`(`id`, `first_name`, `last_name`, `username`, `email`, `password`, `gender`, `created_at`) values 
(1, "Nenad" ,"Pantelic" , "sone", "pantelija@gmail.com", "$2a$10$8yWc0CpfBVQBmktAATyhxOlqpj9Sbxu9NKfN.JedG1tbsByEGd.dm", "MALE", current_timestamp());
-- BUYER
insert into `user`(`id`, `first_name`, `last_name`, `username`, `email`, `password`, `gender`, `created_at`) values 
(2, "Nenad" ,"Pantelic" , "sone_kupac", "pantelija2@gmail.com", "$2a$10$8yWc0CpfBVQBmktAATyhxOlqpj9Sbxu9NKfN.JedG1tbsByEGd.dm", "MALE", current_timestamp());

-- plain text password is 12345

-- user_role
-- ADMIN, BUYER
insert into `user_role`(`user_id`, `role_id`) values (1, 1), (2,2);



/*  3) Book MS database */

use Code9_Book_Service_Book_Store;

-- genres
insert into `genre`(`id`, `name`, `created_at`, `updated_at`) values
										(1, "thriller", current_timestamp(), current_timestamp()),
										 (2,"drama", current_timestamp(), current_timestamp()),
										 (3, "sci-fi", current_timestamp(), current_timestamp()),
                                         (4, "love", current_timestamp(), current_timestamp()),
                                         (5, "horror", current_timestamp(), current_timestamp()),
                                         (6, "fantasy", current_timestamp(), current_timestamp()),
                                         (7, "poetry", current_timestamp(), current_timestamp()),
                                         (8, "adventure", current_timestamp(), current_timestamp());
                                         
-- books
                                     
insert into `book`(`id`, `title`, `description`, `publisher`, `release_date`, `price`, 	`quantity`, `created_at`, `updated_at`, `is_active`)
 values (1, "Harry Potter and Order of Phoenix", "War between Order of the Phoenix and DeathEathers starts", "RandomPublisher",
 current_date(), 1000, 100, current_timestamp(), current_timestamp(), 1);
										 
insert into `book`(`id`, `title`, `description`, `publisher`, `release_date`, `price`, `quantity`,`created_at`, `updated_at`, `is_active`)
 values (2, "Fellowship of the Ring", "The journey starts in Rivendell", "RandomPublisher",
 current_date(), 1200, 100, current_timestamp(), current_timestamp(), 1);
 
 -- book_genre
 -- Harry Potter -> children and adventure
-- LOTR -> adventure
insert into book_genre(book_id, genre_id) values(1, 8), (1, 6), (2,8);

-- authors

-- 1 -> JK Rolling
-- 2 -> JR Tolkien
-- 3 -> Bilbo Baggins
insert into author values(1), (2), (3);					                                         
										                                         
										                                         
-- book_author
-- Harry Potter -> JK Rolling
-- LOTR -> JR Tolkien, Bilbo Baggins

insert into book_author(book_id, author_id) values(1, 1), (2, 2),(2,3);

/* 4) Author MS database */
use Code9_Author_Service_Book_Store;

-- authors
insert into `author`(`id`, `first_name`, `last_name`, `resume`, `created_at`, `updated_at`)
 values (1, "Joanne Kathleen", "Rolling", "The best children books author", current_timestamp(), current_timestamp());

insert into `author`(`id`, `first_name`, `last_name`, `resume`, `created_at`, `updated_at`)
 values (2, "JR", "Tolkien", "Nominee for the Nobel prize", current_timestamp(), current_timestamp());
										 
insert into `author`(`id`, `first_name`, `last_name`, `resume`, `created_at`, `updated_at`)
 values (3, "Bilbo", "Baggins", "Imaginary character", current_timestamp(), current_timestamp());
	     
	     
-- books 
-- Harry Potter (some book from the series)
-- LOTR - Fellowship of the Ring
insert into book values (1), (2);                  

-- author_book
-- Harry Potter (some book from the series)
-- LOTR - Fellowship of the Ring
insert into author_book(author_id, book_id) values (1, 1), (2, 2), (3, 2);

                  
