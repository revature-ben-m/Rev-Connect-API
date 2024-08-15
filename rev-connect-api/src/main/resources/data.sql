drop table if exists users cascade;

create table users(
    id BIGINT AUTO_INCREMENT PRIMARY KEY, 
    username VARCHAR(255) NOT NULL UNIQUE 
    firstName VARCHAR(255)  
    lastName VARCHAR(255)  
    email VARCHAR(255) NOT NULL UNIQUE 
    userPwd VARCHAR(255) NOT NULL 
    isBusiness BOOLEAN 
);


insert into users(id, username, firstName, lastName, email, password, userPwd, isBusiness) values(9999, 'johndoe', 'John', 'Doe','test@email.com', 'password',  true);
insert into users(id, username, firstName, lastName, email, password, userPwd, isBusiness) values(9998, 'janedoe', 'Jane', 'Doe', 'test2@email.com', 'passwords', true);