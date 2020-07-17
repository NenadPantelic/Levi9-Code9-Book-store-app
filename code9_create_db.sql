/* -----------  DDL -------------- */
/*  1) Auth MS database */

-- database
drop database if exists Code9_Auth_Service_Book_Store;
create database Code9_Auth_Service_Book_Store;

-- user
drop user if exists "code9_user_auths"@"localhost";
create user "code9_user_auths"@"localhost" identified by "]8anTNz]}Gk6^Uk!Mp25";
grant all privileges on Code9_Auth_Service_Book_Store.* to "code9_user_auths"@"localhost";
flush privileges;


/*  2) User MS database */

-- database
drop database if exists Code9_User_Service_Book_Store;
create database Code9_User_Service_Book_Store;

-- user
drop user if exists "code9_user_users"@"localhost";
create user "code9_user_users"@"localhost" identified by "~7QjbZkG=+M45SwNASmn";
grant all privileges on Code9_User_Service_Book_Store.* to "code9_user_users"@"localhost";
flush privileges;

/* 3) Book MS database */

-- database
drop database if exists Code9_Book_Service_Book_Store;
create database Code9_Book_Service_Book_Store;

-- user
drop user if exists "code9_user_books"@"localhost";
create user "code9_user_books"@"localhost" identified by "tDZQp]Q8JxZDNpZ77(ey";
grant all privileges on Code9_Book_Service_Book_Store.* to "code9_user_books"@"localhost";
flush privileges;


/* 4) Author MS database */

-- database
drop database if exists Code9_Author_Service_Book_Store;
create database Code9_Author_Service_Book_Store;

-- user
drop user if exists "code9_user_authors"@"localhost";
create user "code9_user_authors"@"localhost" identified by "Vj]zA7;K}qb]-kC2xLGv";
grant all privileges on Code9_Author_Service_Book_Store.* to "code9_user_authors"@"localhost";
flush privileges;


/* 5) Shopping MS database */

-- database
drop database if exists Code9_Shopping_Service_Book_Store;
create database Code9_Shopping_Service_Book_Store;

-- user
drop user if exists "code9_user_shoppings"@"localhost";
create user "code9_user_shoppings"@"localhost" identified by "CSL6[8ufyhKLR7y=c{7:";
grant all privileges on Code9_Shopping_Service_Book_Store.* to "code9_user_shoppings"@"localhost";
flush privileges;
