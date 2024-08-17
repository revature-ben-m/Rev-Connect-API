drop table if exists users cascade;
drop table if exists post;


CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    is_business BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

create table post (
    post_id bigint auto_increment primary key,
    posted_by bigint,
    post_text varchar(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    constraint fk_posted_by foreign key (posted_by) references users(user_id)
);

--Starting test values with ids of 9999 to avoid test issues
insert into users (username, user_password, email, first_name, last_name, is_business, created_at, updated_at)
values
('testuser1', 'hashed_password', 'test1@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser2', 'hashed_password', 'test2@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser3', 'hashed_password', 'test3@mail.com', 'test', 'tester', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser4', 'hashed_password', 'test4@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO post (posted_by, post_text, created_at, updated_at)
VALUES
(1, 'This is the first test post.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'This is the second test post.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Another post for testing.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Yet another test post.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);