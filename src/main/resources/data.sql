drop table if exists users cascade;
drop table if exists post;

create table users(
    id BIGINT AUTO_INCREMENT PRIMARY KEY, 
    username VARCHAR(255) NOT NULL UNIQUE,
    firstName VARCHAR(255),  
    lastName VARCHAR(255),  
    email VARCHAR(255) NOT NULL UNIQUE, 
    userPwd VARCHAR(255) NOT NULL, 
    isBusiness BOOLEAN 
);

create table post (
    postId int primary key auto_increment,
    postedBy int,
    postText varchar(255),
    timePostedEpoch bigint,
    foreign key (postedBy) references user(userId)
);

--Starting test values with ids of 9999 to avoid test issues
insert into users values (9999, 'testuser1', 'password');
insert into users values (9998, 'testuser2', 'password');
insert into users values (9997, 'testuser3', 'password');
insert into users values (9996, 'testuser4', 'password');

insert into post values (9999, 9999,'test post 1',1669947792);
insert into post values (9997, 9997,'test post 2',1669947792);
insert into post values (9996, 9996,'test post 3',1669947792);