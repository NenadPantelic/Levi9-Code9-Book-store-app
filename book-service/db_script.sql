-- database
drop database if exists Code9_Book_Service_Book_Store;
create database Code9_Book_Service_Book_Store;

-- user
drop user if exists "code9_user_books"@"localhost";
create user "code9_user_books"@"localhost" identified by "tDZQp]Q8JxZDNpZ77(ey";
grant all privileges on Code9_Book_Service_Book_Store.* to "code9_user_books"@"localhost";
flush privileges;
