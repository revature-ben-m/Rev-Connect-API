-- Drop the tables if they exist
DROP TABLE IF EXISTS connection_requests;
DROP TABLE IF EXISTS system_users;

-- Create the system_users table
CREATE TABLE system_users (
    accountId INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255)
);

-- Insert test data into system_users
INSERT INTO system_users (accountId, email, username, password) VALUES 
(1, 'anjineyulu215@revature.net', 'anjineyulu215', 'anjineyulu'),
(2, 'asif650@revature.net', 'asif650', 'asif'),
(3, 'benjamin346@revature.net', 'benjamin346', 'benjamin'),
(4, 'christopherjoseph850@revature.net', 'testuser4', 'password'),
(5, 'gautam746@revature.net', 'gautam746', 'gautam'),
(6, 'matt392@revature.net', 'matt392', 'matt'),
(7, 'mohamed019@revature.net', 'mohamed019', 'mohamed'),
(8, 'mohan863@revature.net', 'mohan863', 'mohan'),
(9, 'natnael035@revature.net', 'natnael035', 'natnael'),
(10, 'olufifunmi957@revature.net', 'olufifunmi957', 'olufifunmi'),
(11, 'rachana153@revature.net', 'rachana153', 'rachana'),
(12, 'trevor689@revature.net', 'trevor689', 'trevor'),
(13, 'yonas905@revature.net', 'yonas905', 'yonas'),
(14, 'nickolas.jurczak@revature.com', 'nickolas.jurczak', 'jurczak'),
(15, 'phone329@revature.net', 'phone329', 'phone'),
(16, 'sarangi604@revature.net', 'sarangi604', 'sarangi');

-- Create the connection_requests table
CREATE TABLE connection_requests (
    connection_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    requester_id INT NOT NULL,
    recipient_id INT NOT NULL,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints to ensure referential integrity
    FOREIGN KEY (requester_id) REFERENCES system_users(accountId),
    FOREIGN KEY (recipient_id) REFERENCES system_users(accountId)
);
-- Insert sample data into connection_requests
INSERT INTO connection_requests (requester_id, recipient_id, status) VALUES
(1, 2, 'PENDING'),       -- anjineyulu215 requested a connection with asif650
(3, 4, 'ACCEPTED'),      -- benjamin346 requested a connection with testuser4, and it was accepted
(5, 6, 'DECLINED'),      -- gautam746 requested a connection with matt392, but it was declined
(7, 8, 'PENDING'),       -- mohamed019 requested a connection with mohan863
(9, 10, 'PENDING'),      -- natnael035 requested a connection with olufifunmi957
(11, 12, 'ACCEPTED'),    -- rachana153 requested a connection with trevor689, and it was accepted
(13, 14, 'PENDING'),     -- yonas905 requested a connection with nickolas.jurczak
(15, 16, 'DECLINED');    -- phone329 requested a connection with sarangi604, but it was declined

