CREATE TABLE USERS
(
 user_id bigint(20) AUTO_INCREMENT,
 firstname varchar(255),
 lastName varchar(255),
 birthday DATE,
 gender varchar(10),
 interests varchar(512),
 city varchar (20)
);
CREATE TABLE FRIENDS
(
 user_id bigint(20),
 friend_id bigint(20)
);
CREATE TABLE CLIENTS
(
 user_id bigint(20),
 login varchar(255),
 password varchar(255),
 token varchar(MAX),
 FOREIGN KEY (user_id) REFERENCES USERS(user_id)
);