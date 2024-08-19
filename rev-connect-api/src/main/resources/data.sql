DROP TABLE IF EXISTS businessprofile;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255),
    is_business BOOLEAN DEFAULT false
);

CREATE TABLE businessprofile (
    profile_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT UNIQUE NOT NULL REFERENCES users(id),
    bio_text VARCHAR(500)
);

INSERT INTO users VALUES (111, 'test1', 'password1', 'joe1', 'doe1', 'test1@email', true);
INSERT INTO users VALUES (112, 'test2', 'password2', 'joe2', 'doe2', 'test2@email', true);
INSERT INTO users VALUES (113, 'test3', 'password3', 'joe3', 'doe3', 'test3@email', true);
INSERT INTO users VALUES (114, 'test4', 'password4', 'joe4', 'doe4', 'test4@email', false);
INSERT INTO users VALUES (115, 'test5', 'password5', 'joe5', 'doe5', 'test5@email', false);
INSERT INTO users VALUES (116, 'test6', 'password6', 'joe6', 'doe6', 'test6@email', false);

INSERT INTO businessprofile VALUES (999, 111, 'Test Bio 1');
INSERT INTO businessprofile VALUES (998, 112, 'Test Bio 2');
INSERT INTO businessprofile VALUES (997, 113, 'Test Bio 3');
INSERT INTO businessprofile VALUES (996, 114, 'Test Bio 4');
INSERT INTO businessprofile VALUES (995, 115, 'Test Bio 5');
INSERT INTO businessprofile VALUES (994, 116, 'Test Bio 6');
