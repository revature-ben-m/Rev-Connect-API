drop table if exists post;
drop table if exists user;

create table post (
    postId int primary key auto_increment,
    postedBy int,
    postText varchar(255),
    timePostedEpoch bigint,
    foreign key (postedBy) references user(userId)
);
create table user (
    userId int primary key auto_increment,
    username varchar(255) not null unique,
    password varchar(255)
);

--Starting test values with ids of 9999 to avoid test issues
insert into user values (9999, 'testuser1', 'password');
insert into user values (9998, 'testuser2', 'password');
insert into user values (9997, 'testuser3', 'password');
insert into user values (9996, 'testuser4', 'password');

insert into post values (9999, 9999,'test post 1',1669947792);
insert into post values (9997, 9997,'test post 2',1669947792);
insert into post values (9996, 9996,'test post 3',1669947792);