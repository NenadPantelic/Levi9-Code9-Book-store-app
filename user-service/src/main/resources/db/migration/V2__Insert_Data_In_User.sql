use Code9_User_Service_Book_Store;
-- ADMIN
insert into `user`(`id`, `first_name`, `last_name`, `username`, `email`, `password`, `gender`, `created_at`) values 
(1, "Nenad" ,"Pantelic" , "sone", "pantelija@gmail.com", "$2a$10$8yWc0CpfBVQBmktAATyhxOlqpj9Sbxu9NKfN.JedG1tbsByEGd.dm", "MALE", current_timestamp());
-- BUYER
insert into `user`(`id`, `first_name`, `last_name`, `username`, `email`, `password`, `gender`, `created_at`) values 
(2, "Nenad" ,"Pantelic" , "sone_kupac", "pantelija2@gmail.com", "$2a$10$8yWc0CpfBVQBmktAATyhxOlqpj9Sbxu9NKfN.JedG1tbsByEGd.dm", "MALE", current_timestamp());

-- plain text password is 12345
