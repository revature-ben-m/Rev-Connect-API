DROP TABLE IF EXISTS businessprofile;
CREATE TABLE businessprofile (
    profile_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT UNIQUE NOT NULL,
    bio_text VARCHAR(500)
);

INSERT INTO businessprofile VALUES (999, 111, 'Test Bio 1');
INSERT INTO businessprofile VALUES (998, 112, 'Test Bio 2');
INSERT INTO businessprofile VALUES (997, 113, 'Test Bio 3');
INSERT INTO businessprofile VALUES (996, 114, 'Test Bio 4');
INSERT INTO businessprofile VALUES (995, 115, 'Test Bio 5');
INSERT INTO businessprofile VALUES (994, 116, 'Test Bio 6');

DROP TABLE IF EXISTS endorsement_links;
CREATE TABLE endorsement_links (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    link VARCHAR(255) NOT NULL,
    link_text VARCHAR(255),

    FOREIGN KEY (user_id) REFERENCES businessprofile(user_id)
);

INSERT INTO endorsement_links VALUES (1, 111, 'Https://www.blahblablah.com', 'test_link');