drop table if exists userAccount cascade;

create table userAccount(
    userId int primary key auto_increment,
    firstName varchar(255),
    lastName varchar(255),
    email varchar(255),
    username varchar(255) not null unique,
    password varchar(255) not null,
    phoneNumber varchar(255),
    accountType varchar(255)
);

insert into userAccount(userId, firstName, lastName, email, username, password, phoneNumber, accountType) values(2, 'John', 'Doe','test@email.com', 'johndoe', 'password', '1234567890', 'personal');
insert into userAccount(userId, firstName, lastName, email, username, password, phoneNumber, accountType) values(3, 'Jane', 'Doe', 'test2@email.com', 'janedoe', 'passwords', '1234567891', 'business');