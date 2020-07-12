use Code9_Book_Service_Book_Store;
insert into `book`(`id`, `title`, `description`, `publisher`, `release_date`, `price`, 	`quantity`, `created_at`, `updated_at`, `is_active`)
 values (1, "Harry Potter and Order of Phoenix", "War between Order of the Phoenix and DeathEathers starts", "RandomPublisher",
 current_date(), 1000, 100, current_timestamp(), current_timestamp(), 1);
										 
insert into `book`(`id`, `title`, `description`, `publisher`, `release_date`, `price`, `quantity`,`created_at`, `updated_at`, `is_active`)
 values (2, "Fellowship of the Ring", "The journey starts in Rivendell", "RandomPublisher",
 current_date(), 1200, 100, current_timestamp(), current_timestamp(), 1);
										 
