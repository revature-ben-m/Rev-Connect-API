drop table if exists system_users;
create table system_users (
    accountId int primary key auto_increment,
    email varchar(255) not null unique,
    username varchar(255) not null unique,
    password varchar(255),
   resetToken VARCHAR(255)
);

-- Starting test values with ids of 9999 to avoid test issues
insert into system_users values (1, 'anjineyulu215@revature.net','anjineyulu215', 'anjineyulu',NULL);
insert into system_users values (2, 'asif650@revature.net','asif650', 'asif',NULL);
insert into system_users values (3, 'benjamin346@revature.net','benjamin346', 'benjamin', NULL);
insert into system_users values (4, 'christopherjoseph850@revature.net','testuser4', 'password', NULL);
insert into system_users values (5, 'gautam746@revature.net','gautam746', 'gautam',NULL);
insert into system_users values (6, 'matt392@revature.net','matt392', 'matt',NULL);
insert into system_users values (7, 'mohamed019@revature.net','mohamed019', 'mohamed',NULL);
insert into system_users values (8, 'mohan863@revature.net','mohan863', 'mohan',NULL);
insert into system_users values (9, 'natnael035@revature.net','natnael035', 'natnael',NULL);
insert into system_users values (10, 'olufifunmi957@revature.net','olufifunmi957', 'olufifunmi',NULL);
insert into system_users values (11, 'rachana153@revature.net','rachana153', 'rachana',NULL);
insert into system_users values (12, 'trevor689@revature.net','trevor689', 'trevor',NULL);
insert into system_users values (13, 'yonas905@revature.net','yonas905', 'yonas',NULL);
insert into system_users values (14, 'nickolas.jurczak@revature.com','nickolas.jurczak', 'jurczak',NULL);
insert into system_users values (15, 'phone329@revature.net','phone329', 'phone',NULL);
insert into system_users values (16, 'sarangi604@revature.net','sarangi604', 'sarangi',NULL);