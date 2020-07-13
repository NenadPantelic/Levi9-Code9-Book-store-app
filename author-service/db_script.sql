-- database
drop database if exists Code9_Author_Service_Book_Store;
create database Code9_Author_Service_Book_Store;

-- user
drop user if exists "code9_user_authors"@"localhost";
create user "code9_user_authors"@"localhost" identified by "Vj]zA7;K}qb]-kC2xLGv";
grant all privileges on Code9_Author_Service_Book_Store.* to "code9_user_authors"@"localhost";
flush privileges;
