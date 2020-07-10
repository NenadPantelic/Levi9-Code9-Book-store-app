-- database
drop database if exists Code9_User_Service_Book_Store;
create database Code9_User_Service_Book_Store;

-- user
drop user if exists "code9_user_users"@"localhost";
create user "code9_user_users"@"localhost" identified by "~7QjbZkG=+M45SwNASmn";
grant all privileges on Code9_User_Service_Book_Store.* to "code9_user_users"@"localhost";
flush privileges;
