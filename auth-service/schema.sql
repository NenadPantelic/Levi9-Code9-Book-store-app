-- database
drop database if exists Code9_Auth_Service_Book_Store;
create database Code9_Auth_Service_Book_Store;

-- user
drop user if exists "code9_user_auths"@"localhost";
create user "code9_user_auths"@"localhost" identified by "]8anTNz]}Gk6^Uk!Mp25";
grant all privileges on Code9_Auth_Service_Book_Store.* to "code9_user_auths"@"localhost";
flush privileges;