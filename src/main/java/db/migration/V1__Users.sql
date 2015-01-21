create table Users(
name varchar(12) not null,
password varchar(255) not null,
salt varchar(255) not null,
PRIMARY KEY (name)
);