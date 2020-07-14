-- database
drop database if exists Code9_Shopping_Service_Book_Store;
create database Code9_Shopping_Service_Book_Store;

-- user
drop user if exists "code9_user_shoppings"@"localhost";
create user "code9_user_shoppings"@"localhost" identified by "CSL6[8ufyhKLR7y=c{7:";
grant all privileges on Code9_Shopping_Service_Book_Store.* to "code9_user_shoppings"@"localhost";
flush privileges;
